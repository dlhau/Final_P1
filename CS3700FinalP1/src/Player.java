import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * David Hau
 * CS3700
 * 12/7/2018
 */

public class Player
{
	private static final int PLAYER_ONE_PORT1 = 4011;
	private static final int PLAYER_ONE_PORT2 = 4012;
	private static final int PLAYER_TWO_PORT1 = 4021;
	private static final int PLAYER_TWO_PORT2 = 4022;
	private static final int PLAYER_THREE_PORT1 = 4031;
	private static final int PLAYER_THREE_PORT2 = 4032;

	private Callable<Boolean> connectionOne;
	private Callable<Boolean> connectionTwo;
	Future<Boolean> futureOne;
	Future<Boolean> futureTwo;

	Player(int playerNum) throws UnknownHostException, IOException
	{
		if (playerNum == 1)
		{
			connectionOne = new GameConnection(PLAYER_ONE_PORT1, PLAYER_TWO_PORT1);
			connectionTwo = new GameConnection(PLAYER_ONE_PORT2, PLAYER_THREE_PORT1);
		}
		else if (playerNum == 2)
		{
			connectionOne = new GameConnection(PLAYER_TWO_PORT1, PLAYER_ONE_PORT1);
			connectionTwo = new GameConnection(PLAYER_TWO_PORT2, PLAYER_THREE_PORT2);
		}
		else if (playerNum == 3)
		{
			connectionOne = new GameConnection(PLAYER_THREE_PORT1, PLAYER_ONE_PORT2);
			connectionTwo = new GameConnection(PLAYER_THREE_PORT2, PLAYER_TWO_PORT2);
		}

		ExecutorService executor = Executors.newSingleThreadExecutor();
		futureOne = executor.submit(connectionOne);
		futureTwo = executor.submit(connectionTwo);
		executor.shutdown();
	}

	private class GameConnection extends DatagramSocket implements Callable<Boolean>
	{
		private static final String HOST = "localhost";
		private int targetPort;

		GameConnection(int sourcePort, int targetPort) throws UnknownHostException, IOException
		{
			super(sourcePort);
			this.targetPort = targetPort;
		}

		public Boolean call() throws Exception
		{
			Move localDecision = Move.getRandom();
			Move remoteDecision = null;
			try
			{
				remoteDecision = sendAndReceive(localDecision);
				disconnect();
				close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return localDecision.beats(remoteDecision);
		}

		private Move sendAndReceive(Move localDecision) throws IOException
		{
			byte[] buffer = new byte[1];
			buffer[0] = (byte) localDecision.ordinal();

			DatagramPacket out = new DatagramPacket(buffer, buffer.length,
					InetAddress.getByName(HOST), targetPort);
			send(out);

			buffer = new byte[1];
			DatagramPacket response = new DatagramPacket(buffer, buffer.length);
			receive(response);

			return Move.values()[buffer[0]];
		}
	}

}