/*
 * David Hau
 * CS3700
 * 12/7/2018
 */

public enum Move
{
	ROCK("ROCK")
	{
		@Override
		public boolean beats(Move move)
		{
			return move == SCISSORS;
		}
	},
    PAPER("PAPER")
    {
		@Override
		public boolean beats(Move move)
		{
			return move == ROCK;
		}
	},
    SCISSORS("SCISSORS")
    {
		@Override
		public boolean beats(Move move)
		{
			return move == PAPER;
		}
	};
	
	private final String move;
	
	Move(String move) { this.move = move; }
	public String toString() { return this.move; }
	public abstract boolean beats(Move move);
	public static Move getRandom() { return values()[(int) (Math.random() * Move.values().length)];
	}
}