package com.aranaira.arcanearchives.util.types;

import com.aranaira.arcanearchives.util.types.ManifestEntry;
import net.minecraft.item.ItemStack;

/**
 * Simple helper class for storing an item stack, a dimension and an item entry.
 * It's a bit like putting pouches inside of boxes inside of crates, but it works.
 */
public class ManifestItemEntry {
	public ItemStack stack;
	public int dim;
	public ManifestEntry.ItemEntry entry;

	public ManifestItemEntry (ItemStack stack, int dim, ManifestEntry.ItemEntry entry) {
		this.stack = stack;
		this.dim = dim;
		this.entry = entry;
	}
}