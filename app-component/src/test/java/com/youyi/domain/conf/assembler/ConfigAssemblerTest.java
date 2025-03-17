package com.youyi.domain.conf.assembler;

import com.youyi.domain.conf.model.ConfigDO;
import com.youyi.domain.conf.request.ConfigUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/31
 */
public class ConfigAssemblerTest {

    @Test
    void testUpdateParamToDO() {
        ConfigUpdateRequest param = new ConfigUpdateRequest();
        param.setConfigKey("configKey");
        param.setNewConfigValue("newConfigValue");
        param.setCurrVersion(1);

        ConfigDO configDO = ConfigAssembler.CONFIG_ASSEMBLER.toDO(param);

        Assertions.assertNotNull(configDO);
        Assertions.assertEquals("configKey", configDO.getConfigKey());
        Assertions.assertEquals("newConfigValue", configDO.getConfigValue());
        Assertions.assertEquals(1, configDO.getVersion());
    }
}
