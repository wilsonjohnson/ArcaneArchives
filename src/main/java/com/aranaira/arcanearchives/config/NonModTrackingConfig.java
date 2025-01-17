package com.aranaira.arcanearchives.config;

import com.aranaira.arcanearchives.ArcaneArchives;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.common.config.Config;

import java.util.ArrayList;
import java.util.List;

@Config.LangKey("arcanearchives.config.non_mod_tracking")
@Config(modid= ArcaneArchives.MODID, name="arcanearchives/mixins")
public class NonModTrackingConfig {
	@Config.LangKey("arcanearchives.config.non_mod_tracking.ignore")
	public static String[] ContainerClasses = new String[]{};

	@Config.Ignore
	private static List<Class> containerClasses = null;

	public static List<Class> getContainerClasses () {
		if (containerClasses == null) {
			Class<?> container = GuiContainer.class;
			containerClasses = new ArrayList<>();
			for (String clazz : ContainerClasses) {
				Class<?> clz;
				try {
					clz = Class.forName(clazz);
				} catch (ClassNotFoundException e) {
					ArcaneArchives.logger.debug("Unable to resolve class " + clazz + " so skipping.");
					continue;
				}

				if (clz.isAssignableFrom(container)) {
					containerClasses.add(clz);
				} else {
					ArcaneArchives.logger.debug("Skipping " + clazz + " as it does not derive from GuiContainer.");
				}
			}
		}

		return containerClasses;
	}

	@Config.LangKey("arcanearchives.config.non_mod_tracking.disable")
	public static boolean DisableMixinHighlight = false;
}
