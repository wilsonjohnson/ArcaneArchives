package com.aranaira.arcanearchives.init;

import com.aranaira.arcanearchives.blocks.*;
import com.aranaira.arcanearchives.tileentities.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import java.util.*;
import java.util.stream.Stream;

public class BlockLibrary
{

	public static final List<BlockTemplate> BLOCKS = new ArrayList<>();

	//Matrices
	public static final MatrixCrystalCore MATRIX_CRYSTAL_CORE = new MatrixCrystalCore();
	public static final MatrixRepository MATRIX_REPOSITORY = new MatrixRepository();
	public static final MatrixReservoir MATRIX_RESERVOIR = new MatrixReservoir();
	public static final MatrixStorage MATRIX_STORAGE = new MatrixStorage();
	public static final MatrixDistillate MATRIX_DISTILLATE = new MatrixDistillate(); //TODO: Check if Thaumcraft is loaded

	//Blocks
	public static final StorageRawQuartz STORAGE_RAW_QUARTZ = new StorageRawQuartz();
	public static final StorageCutQuartz STORAGE_CUT_QUARTZ = new StorageCutQuartz();
	public static final RadiantChest RADIANT_CHEST = new RadiantChest();
	public static final RadiantCraftingTable RADIANT_CRAFTING_TABLE = new RadiantCraftingTable();
	public static final RadiantLantern RADIANT_LANTERN = new RadiantLantern();
	public static final RadiantResonator RADIANT_RESONATOR = new RadiantResonator();
	public static final RawQuartz RAW_QUARTZ = new RawQuartz();
	public static final DominionCrystal DOMINION_CRYSTAL = new DominionCrystal();
	public static final GemcuttersTable GEMCUTTERS_TABLE = new GemcuttersTable();
	public static final AccessorBlock ACCESSOR = new AccessorBlock();

	public static final BiMap<Integer, BlockTemplate> BLOCK_BIMAP = HashBiMap.create();

	static
	{
		List<Block> blocks = Arrays.asList(MATRIX_CRYSTAL_CORE, MATRIX_REPOSITORY, MATRIX_RESERVOIR, MATRIX_STORAGE, MATRIX_DISTILLATE, STORAGE_RAW_QUARTZ, STORAGE_CUT_QUARTZ, RADIANT_CHEST, RADIANT_CRAFTING_TABLE, RADIANT_LANTERN, RADIANT_RESONATOR, RAW_QUARTZ, DOMINION_CRYSTAL, GEMCUTTERS_TABLE, ACCESSOR);
		for (int i = 0; i < blocks.size(); i++) {
			BLOCK_BIMAP.put(i, (BlockTemplate) blocks.get(i));
		}

		// Accessor is included in the BiMap in the unlikely event that we use it for anything else.
	}

	// Tile Entities. TODO: Don't forget to update the RegistryHandler when adding new ones.
	public static final AATileEntity RADIANT_RESONATOR_TILE_ENTITY = new RadiantResonatorTileEntity();
	public static final AATileEntity MATRIX_CORE_TILE_ENTITY = new MatrixCoreTileEntity();
	public static final AATileEntity MATRIX_REPOSITORY_TILE_ENTITY = new MatrixRepositoryTileEntity();
	public static final AATileEntity ACCESSOR_TILE_ENTITY = new AccessorTileEntity();
	public static final AATileEntity RADIANT_CHEST_TILE_ENTITY = new RadiantChestTileEntity();
	public static final AATileEntity GEMCUTTERS_TABLE_TILE_ENTITY = new GemcuttersTableTileEntity();
	public static final AATileEntity RADIANT_CRAFTING_TABLE_TILE_ENTITY = new RadiantCraftingTableTileEntity();
	public static final AATileEntity MATRIX_STORAGE_TILE_ENTITY = new MatrixStorageTileEntity();

	public static final List<AATileEntity> TILES = new ArrayList<>();

	static
	{
		TILES.addAll(Arrays.asList(RADIANT_RESONATOR_TILE_ENTITY, MATRIX_CORE_TILE_ENTITY, MATRIX_REPOSITORY_TILE_ENTITY, ACCESSOR_TILE_ENTITY, RADIANT_CHEST_TILE_ENTITY, GEMCUTTERS_TABLE_TILE_ENTITY, RADIANT_CRAFTING_TABLE_TILE_ENTITY, MATRIX_STORAGE_TILE_ENTITY));
	}

}
