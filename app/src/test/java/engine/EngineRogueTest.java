package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.cemi.engine.Engine;
import com.cemi.engine.system.LocalizationManager;

class EngineRogueTest {

    @Test
    public void engineTest() {
        assertEquals(1, 1);
    }

    @Test
    void localizationManagerTest() {
        LocalizationManager.init();
        LocalizationManager.setLanguage("en");
        assertEquals("Play", LocalizationManager.getLocalizedString("play"));
        LocalizationManager.setLanguage("pl");
        assertEquals("Graj", LocalizationManager.getLocalizedString("play"));
    }
}
