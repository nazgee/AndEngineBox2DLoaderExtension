package eu.nazgee.box2dloader.entities;

import org.andengine.opengl.vbo.VertexBufferObjectManager;

import eu.nazgee.box2dloader.recipes.IRecipeEntity;
import eu.nazgee.box2dloader.recipes.RecipeEntity;

class FactoryWorkerPhysicsAwareEntity extends FactoryWorkerEntity {
	public FactoryWorkerPhysicsAwareEntity(VertexBufferObjectManager pVBO, IFactoryWorkerEntity ... helpers) {
		super(pVBO, helpers);
	}

	@Override
	public boolean understandsRecipe(IRecipeEntity pRecipe) {
		return (pRecipe instanceof RecipeEntity);
	}

	@Override
	public IPhysicsAwareEntity build(IRecipeEntity pRecipe) {
		RecipeEntity stub = ((RecipeEntity) pRecipe);
		IPhysicsAwareEntity product = new PhysicsAwareEntity(stub, stub.getX(), stub.getY());
		configure(pRecipe, product);
		return product;
	}
}