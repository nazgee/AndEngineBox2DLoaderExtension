package eu.nazgee.box2dloader.stubs;

import org.andengine.util.call.ParameterCallable;

public interface IStubParameterCallable extends ParameterCallable<IStub> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	@Override
	public void call(final IStub pEntity);
}