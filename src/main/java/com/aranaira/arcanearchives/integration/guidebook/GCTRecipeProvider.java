package com.aranaira.arcanearchives.integration.guidebook;

import com.aranaira.arcanearchives.ArcaneArchives;
import com.aranaira.arcanearchives.api.IGCTRecipe;
import com.aranaira.arcanearchives.recipe.IngredientStack;
import com.aranaira.arcanearchives.recipe.gct.GCTRecipeList;
import gigaherz.lirelent.guidebook.guidebook.drawing.VisualElement;
import gigaherz.lirelent.guidebook.guidebook.elements.ElementImage;
import gigaherz.lirelent.guidebook.guidebook.elements.ElementStack;
import gigaherz.lirelent.guidebook.guidebook.recipe.RecipeProvider;
import gigaherz.lirelent.guidebook.guidebook.util.Size;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class GCTRecipeProvider extends RecipeProvider {
	private static final ResourceLocation BACKGROUND = new ResourceLocation(ArcaneArchives.MODID, "gui/guidebook/recipe_gct");
	private static final int LEFT_OFFSET = 105;

	@Nullable
	@Override
	public ProvidedComponents provideRecipeComponents (@Nonnull ItemStack targetOutput, int recipeIndex) {
		for (IGCTRecipe recipe : GCTRecipeList.instance.getRecipeList()) {
			if (ItemStack.areItemsEqual(targetOutput, recipe.getRecipeOutput())) {
				return provideRecipeComponents(recipe.getName());
			}
		}
		return null;
	}

	@Nullable
	@Override
	public ProvidedComponents provideRecipeComponents (@Nonnull ResourceLocation recipeKey) {
		IGCTRecipe recipe = GCTRecipeList.instance.getRecipe(recipeKey);
		if (recipe == null) {
			return null;
		}

		ArrayList<ElementStack> stacks = new ArrayList<>();
		VisualElement additionalRenderer = new VisualElement(new Size(), 0, 0, 0) {
		};

		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			IngredientStack stack = recipe.getIngredients().get(i);
			ElementStack inputSlot = new ElementStack(false, false);
			for (ItemStack s : stack.getMatchingStacks()) {
				ItemStack c = s.copy();
				c.setCount(stack.getCount());
				inputSlot.stacks.add(c);
			}

			inputSlot.x = (i % 4) * 22 + 3 + LEFT_OFFSET;
			inputSlot.y = (i / 4) * 22 + 3;
			stacks.add(inputSlot);
		}

		ElementStack outputSlot = new ElementStack(false, false);
		outputSlot.stacks.add(recipe.getRecipeOutput().copy());
		outputSlot.x = 107 + LEFT_OFFSET;
		outputSlot.y = 14;
		stacks.add(outputSlot);

		ElementImage background = new ElementImage(false, false);
		background.textureLocation = BACKGROUND;
		background.x = 0 + LEFT_OFFSET;
		background.y = 0;
		background.tx = 0;
		background.ty = 0;
		background.w = 127;
		background.h = 44;

		return new ProvidedComponents(72, stacks.toArray(new ElementStack[]{}), background, additionalRenderer);
	}
}
