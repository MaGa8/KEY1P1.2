package key1p12.tetris.bot;

import key1p12.tetris.game.Game.SimulGame;

public class TotalHeightPerformance implements PerfMeasure {

	public TotalHeightPerformance (int tolerance)
	{
		mTolerance = tolerance;
	}
	
	@Override
	public double getPerf(SimulGame state) 
	{
		if (state.isGameOver())
			return 0;
		//largest height = lowest value
		int max = state.getHeight();
		for (int cCol = 0; cCol < state.getWidth(); ++cCol)
		{
			int h = state.getFilledHeight(cCol) + 1;
 			if (h < max)
				max = state.getFilledHeight(cCol) + 1;
		}
		
		if (max > state.getHeight() - mTolerance)
			return 1;
		return (double)max / (double)(state.getHeight() - mTolerance);
	}

	@Override
	public boolean mayExceed(SimulGame known, SimulGame test) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private int mTolerance;
}
