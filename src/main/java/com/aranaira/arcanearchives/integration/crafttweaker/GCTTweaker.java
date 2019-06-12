package com.aranaira.arcanearchives.integration.crafttweaker;

import com.aranaira.arcanearchives.ArcaneArchives;
import com.aranaira.arcanearchives.recipe.gct.GCTRecipe;
import com.aranaira.arcanearchives.recipe.gct.GCTRecipeList;
import com.aranaira.arcanearchives.util.types.IngredientStack;
import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseAction;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.CraftTweaker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ZenRegister
@ZenClass("mods." + ArcaneArchives.MODID + ".GCT")
public class GCTTweaker {

	@ZenMethod
	public static void addRecipe (String name, IItemStack output, IIngredient[] inputs) {
		CraftTweaker.LATE_ACTIONS.add(new Add(name, InputHelper.toStack(output), inputs));
	}

	@ZenMethod
	public static void removeRecipe (IItemStack output) {
		CraftTweaker.LATE_ACTIONS.add(new Remove(InputHelper.toStack(output)));
	}

	private static class Remove extends BaseAction {
		private ItemStack output;

		private Remove (ItemStack stack) {
			super("GCT Recipe removal");
			this.output = stack;
		}

		@Override
		public String describe () {
			return "Removing " + output.getItem().getRegistryName().toString();
		}

		@Override
		public void apply () {
			GCTRecipe recipe = GCTRecipeList.getRecipeByOutput(output);
			if (recipe == null) {
				CraftTweakerAPI.logError("Invalid recipe for " + output.getItem().getRegistryName().toString());
			} else {
				GCTRecipeList.removeRecipe(recipe);
			}
		}
	}

	private static class Add extends BaseAction {
		private final ResourceLocation name;
		private final ItemStack output;
		private final IIngredient[] ingredients;

		private Add (String name, ItemStack output, IIngredient[] ingredients) {
			super("GCT Recipe addition");
			this.name = new ResourceLocation(ArcaneArchives.MODID, name + ".ct");
			this.output = output;
			this.ingredients = ingredients;
		}

		@Override
		public void apply () {
			List<IngredientStack> stacks = new ArrayList<>();
			for (IIngredient ingredient : ingredients) {
				stacks.add(new IngredientStack(CraftTweakerMC.getIngredient(ingredient), ingredient.getAmount()));
			}
			GCTRecipe recipe = new GCTRecipe(name, output, stacks);
			GCTRecipeList.addRecipe(recipe);
		}
	}
}
