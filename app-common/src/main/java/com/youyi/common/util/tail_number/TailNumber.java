package com.youyi.common.util.tail_number;

import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.Getter;

/**
 * Tail number configuration for feature gating based on ID modulo and whitelists/blacklists.
 * <p>
 * Configuration format: {@code mod;tailNumbers;whiteList;blackList}
 * <ul>
 *   <li>mod - Modulo value (e.g., 100 for 0-99 tail numbers)</li>
 *   <li>tailNumbers - Ranges of tail numbers to hit (e.g., "1-5,10,20-30")</li>
 *   <li>whiteList - Comma-separated IDs that always hit (optional)</li>
 *   <li>blackList - Comma-separated IDs that never hit (optional)</li>
 * </ul>
 *
 * @author yoyocraft
 * Created on 2026/08/09
 */
public class TailNumber {
    /**
     * Constants for constructor readability when setting default on/off behavior
     */
    public static final boolean DEFAULT_ON = true;
    public static final boolean DEFAULT_OFF = false;

    /**
     * Default configuration with tail number disabled
     */
    public static final TailNumber DEFAULT_OFF_CONFIG = new TailNumber(
        1, null, null
    );

    /**
     * Default configuration with tail number always enabled (mod 1, range [0,0])
     */
    public static final TailNumber DEFAULT_ON_CONFIG = new TailNumber(
        1, ImmutableRangeSet.of(Range.closed(0L, 0L)), null
    );

    private static final String HYPHEN = "-";
    private static final String COMMA = ",";
    private static final String SEMICOLON = ";";

    /**
     * -- GETTER --
     * Gets the pre-hash function
     */
    @Getter protected final LongUnaryOperator preHashFunction;
    /**
     * -- GETTER --
     * Gets the modulo value
     */
    @Getter protected final int mod;
    /**
     * -- GETTER --
     * Gets the tail number ranges (unmodifiable view)
     */
    @Getter private final RangeSet<Long> tailNumbers;
    /**
     * -- GETTER --
     * Gets the whitelist (unmodifiable view)
     */
    @Getter private final Set<Long> whiteList;
    /**
     * -- GETTER --
     * Gets the blacklist (unmodifiable view)
     */
    @Getter private final Set<Long> blackList;
    private final boolean isFullTail;

    /**
     * Creates a config with only whitelist (mod-based routing disabled)
     *
     * @param whiteList set of IDs that always match
     */
    public TailNumber(Set<Long> whiteList) {
        this(0, null, whiteList);
    }

    /**
     * Creates a config with modulo-based tail number routing
     *
     * @param mod         modulo value
     * @param tailNumbers ranges of tail numbers to match
     * @param whiteList   set of IDs that always match
     */
    public TailNumber(int mod, RangeSet<Long> tailNumbers, Set<Long> whiteList) {
        this(null, mod, tailNumbers, whiteList);
    }

    /**
     * Creates a config with pre-hash function, modulo-based routing, and whitelist
     *
     * @param preHashFunction function to apply before modulo
     * @param mod             modulo value
     * @param tailNumbers     ranges of tail numbers to match
     * @param whiteList       set of IDs that always match
     */
    public TailNumber(LongUnaryOperator preHashFunction, int mod, RangeSet<Long> tailNumbers, Set<Long> whiteList) {
        this(preHashFunction, mod, tailNumbers, whiteList, Collections.emptySet());
    }

    /**
     * Full constructor with all options
     *
     * @param preHashFunction function to apply before modulo
     * @param mod             modulo value
     * @param tailNumbers     ranges of tail numbers to match
     * @param whiteList       set of IDs that always match
     * @param blackList       set of IDs that never match
     */
    public TailNumber(
        LongUnaryOperator preHashFunction, int mod, RangeSet<Long> tailNumbers, Set<Long> whiteList, Set<Long> blackList
    ) {
        this.preHashFunction = Optional.ofNullable(preHashFunction).orElse(LongUnaryOperator.identity());
        this.mod = mod;
        this.tailNumbers = Optional.ofNullable(tailNumbers).orElse(ImmutableRangeSet.of());
        // Defensive copying for immutability
        this.whiteList = whiteList == null ? Collections.emptySet() : Set.copyOf(whiteList);
        this.blackList = blackList == null ? Collections.emptySet() : Set.copyOf(blackList);
        // Check if tail numbers cover the full range [0, mod-1] for optimization
        this.isFullTail = mod > 0 && this.tailNumbers.encloses(Range.closed(0L, mod - 1L));
    }

    /**
     * Parses config from string format
     *
     * @param configString config string in format: "mod;tailNumbers;whiteList;blackList"
     * @return parsed TailNumber
     */
    public static TailNumber from(String configString) {
        return from(null, configString);
    }

    /**
     * Parses config from string format with pre-hash function
     *
     * @param preHashFunction optional pre-hash function
     * @param configString    config string in format: "mod;tailNumbers;whiteList;blackList"
     * @return parsed TailNumber
     */
    @Nonnull
    public static TailNumber from(LongUnaryOperator preHashFunction, String configString) {
        configString = configString.trim();
        if (configString.isEmpty()) {
            return DEFAULT_OFF_CONFIG;
        }

        String[] segs = configString.split(SEMICOLON);

        int mod = parseMod(segs[0]);
        RangeSet<Long> tailNumbers = parseTailNumbers(segs);
        Set<Long> whiteList = parseList(segs, 2);
        Set<Long> blackList = parseList(segs, 3);

        return new TailNumber(preHashFunction, mod, tailNumbers, whiteList, blackList);
    }

    private static int parseMod(String modStr) {
        modStr = modStr.trim();
        if (modStr.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(modStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid mod value: " + modStr, e);
        }
    }

    private static RangeSet<Long> parseTailNumbers(String[] segs) {
        if (segs.length < 2 || segs[1].isBlank()) {
            return null;
        }

        try {
            RangeSet<Long> tailNumbers = TreeRangeSet.create();
            String[] ranges = segs[1].split(COMMA);
            for (String range : ranges) {
                range = range.trim();
                if (range.isEmpty()) {
                    continue; // Skip empty segments (e.g., "1,,2")
                }
                if (!range.contains(HYPHEN)) {
                    long num = Long.parseLong(range);
                    // Use canonical to automatically merge adjacent ranges
                    tailNumbers.add(Range.closed(num, num).canonical(DiscreteDomain.longs()));
                } else {
                    String[] nums = range.split(HYPHEN);
                    if (nums.length != 2) {
                        throw new IllegalArgumentException("Invalid range format: " + range);
                    }
                    long lower = Long.parseLong(nums[0].trim());
                    long upper = Long.parseLong(nums[1].trim());
                    if (lower >= upper) {
                        throw new IllegalArgumentException("Invalid range: " + range + " (lower must be less than upper)");
                    }
                    tailNumbers.add(Range.closed(lower, upper).canonical(DiscreteDomain.longs()));
                }
            }
            return tailNumbers;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid tail number format", e);
        }
    }

    private static Set<Long> parseList(String[] segs, int index) {
        if (segs.length <= index || segs[index].isBlank()) {
            return Set.of();
        }

        try {
            Set<Long> list = new LinkedHashSet<>();
            String[] ids = segs[index].split(COMMA);
            for (String id : ids) {
                if (!id.isBlank()) {
                    list.add(Long.parseLong(id.trim()));
                }
            }
            return list;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid list format at segment " + index, e);
        }
    }

    /**
     * Checks if the feature is on for a given ID
     *
     * @param id the ID to check
     * @return true if the feature should be on for this ID
     */
    public boolean isOnFor(long id) {
        // Blacklist check first (highest priority)
        if (!blackList.isEmpty() && blackList.contains(id)) {
            return false;
        }
        // Full tail optimization
        if (isFullTail) {
            return true;
        }
        // Tail number check
        if (mod > 0 && tailNumbers.contains(getTail(id))) {
            return true;
        }
        // Whitelist check (uses original id)
        return whiteList.contains(id);
    }

    /**
     * Computes the tail number for a given ID
     *
     * @param id the input ID
     * @return tail number (0 if mod is 0)
     */
    public long getTail(long id) {
        if (mod > 0) {
            long hashed = preHashFunction.applyAsLong(id);
            return ((hashed % mod) + mod) % mod;
        }
        return 0;
    }

    /**
     * Converts config to string format
     *
     * @return config string in format: "mod;tailNumbers;whiteList;blackList"
     */
    public String toConfigString() {
        StringBuilder sb = new StringBuilder();
        if (mod > 0) {
            sb.append(mod);
        }
        sb.append(SEMICOLON);
        sb.append(tailNumbers.asRanges().stream().map(this::rangeToString).collect(Collectors.joining(COMMA)));
        sb.append(SEMICOLON);
        sb.append(whiteList.stream().map(Object::toString).collect(Collectors.joining(COMMA)));
        if (!blackList.isEmpty()) {
            sb.append(SEMICOLON);
            sb.append(blackList.stream().map(Object::toString).collect(Collectors.joining(COMMA)));
        }
        return sb.toString();
    }

    private String rangeToString(Range<Long> range) {
        StringBuilder sb = new StringBuilder();
        long lower = range.lowerEndpoint();
        if (range.lowerBoundType() == BoundType.OPEN) {
            lower += 1;
        }
        long upper = range.upperEndpoint();
        if (range.upperBoundType() == BoundType.OPEN) {
            upper -= 1;
        }
        if (lower == upper) {
            sb.append(lower);
        } else if (lower < upper) {
            sb.append(lower);
            sb.append(HYPHEN);
            sb.append(upper);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toConfigString();
    }
}
