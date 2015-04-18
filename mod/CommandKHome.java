package mod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.MouseEvent;

public class CommandKHome extends CommandBase
{
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender par1iCommandSender)
	{
		return true;
	}

	@Override
	public String getCommandName()
	{
		// TODO Auto-generated method stub
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		// TODO Auto-generated method stub
		return "/home 谨慎使用每位用户只有一次";
	}

	private void setUserHome(EntityPlayer entityplayer,
			TileEntityChest tileentitychest)
	{

	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		// TODO Auto-generated method stub
		if (icommandsender instanceof EntityPlayerMP)
		{
			EntityPlayerMP entityplayer = (EntityPlayerMP) icommandsender;
			int flag = 0;
			ItemStack itemStack;
			if (entityplayer.worldObj.getBlockId(0, 0, 0) != Block.chest.blockID)
			{
				entityplayer.worldObj.setBlock(0, 0, 0, Block.chest.blockID);
			}
			TileEntityChest tileentitychest = (TileEntityChest) entityplayer.worldObj
					.getBlockTileEntity(0, 0, 0);
			// /////////////////////////////////////////////////////

			for (int i = 0; i < tileentitychest.getSizeInventory(); i++)
			{
				ItemStack tempItemStack = tileentitychest.getStackInSlot(i);
				if ((tempItemStack != null)
						&& (tempItemStack.getTagCompound()
								.getString("userName")
								.equals(entityplayer.username)))
				{

					NBTTagCompound nbtTagCompound = tempItemStack
							.getTagCompound();
					// ////////////////////////////////////////////
					Entity e = Minecraft.getMinecraft().objectMouseOver.entityHit;
					NBTTagCompound tag = new NBTTagCompound();
					e.writeToNBT(tag);
					entityplayer.addChatMessage(tag.toString());
					// ////////////////////////////////////////////
					tempItemStack.setItemName("§4The home of "
							+ entityplayer.username);
					entityplayer.setPositionAndUpdate(
							nbtTagCompound.getDouble("X"),
							nbtTagCompound.getDouble("Y"),
							nbtTagCompound.getDouble("Z"));
					entityplayer.addChatMessage("§2传送成功");
					flag = 1;
					break;
				}
			}
			if (flag == 0)
			{
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setDouble("X", entityplayer.posX);
				nbtTagCompound.setDouble("Y", entityplayer.posY);
				nbtTagCompound.setDouble("Z", entityplayer.posZ);
				nbtTagCompound.setString("userName", entityplayer.username);
				for (int j = 0; j < tileentitychest.getSizeInventory(); j++)
				{
					if (tileentitychest.getStackInSlot(j) == null)
					{
						tileentitychest.setInventorySlotContents(j,
								new ItemStack(Block.sponge));
						tileentitychest.getStackInSlot(j).setTagCompound(
								nbtTagCompound);
						entityplayer.addChatMessage("§2创建传送点成功");
						break;
					}
				}
			}
		}
	}
}
