package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeEntity;

class FactoryPhysicalEntityWorker extends FactoryPhysicalWorker {
	public FactoryPhysicalEntityWorker(VertexBufferObjectManager pVBO, IFactoryPhysicalWorker ... helpers) {
		super(pVBO, helpers);
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeEntity);
	}

	@Override
	public IPhysicalEntity build(IRecipeEntity pRecipe) {
		RecipeEntity stub = ((RecipeEntity) pRecipe);
		IPhysicalEntity product = new PhysicalEntity(stub, stub.getX(), stub.getY());
		configure(pRecipe, product);
		return product;
	}
}