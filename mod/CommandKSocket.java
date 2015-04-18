package mod;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;

public class CommandKSocket extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "socket";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		// TODO Auto-generated method stub
		if (astring.length >= 2) {
			EntityPlayerMP entityplayermp;
			entityplayermp = getCommandSenderAsPlayer(icommandsender);
			try {
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress(astring[0], 6510),200);
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(astring[1].getBytes());
				Thread.sleep(50);
				socket.close();
				entityplayermp.sendChatToPlayer(ChatMessageComponent
						.createFromText("Send:"+astring[1]+" OK"));
			} catch (Exception e) {
				entityplayermp.sendChatToPlayer(ChatMessageComponent
						.createFromText("Send Error"));
			}
		}
	}
}
