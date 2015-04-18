package mod;

import org.bouncycastle.i18n.MessageBundle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.CommandPlaySound;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(name = KendMod.modId, version = "0.1", modid = "Kend Mod")
@NetworkMod(serverSideRequired = true, clientSideRequired = true)
public class KendMod
{
	public static final String modId = "Kend";

	public static Item myItem;

	@Metadata(value = "KendMod")
	public static ModMetadata metadata;

	@Instance("Mod")
	public static KendMod instance;

	public class MyItem extends Item
	{
		public MyItem(int par1)
		{
			super(par1);
			// TODO Auto-generated constructor stub
			setCreativeTab(CreativeTabs.tabTools);
			setTextureName("kend:myItem");
		}

		@Override
		public ItemStack onItemRightClick(ItemStack par1ItemStack,
				World par2World, EntityPlayer par3EntityPlayer)
		{
			// TODO Auto-generated method stub EntityLightningBolt
			par2World.createExplosion(par3EntityPlayer, par3EntityPlayer.posX,
					par3EntityPlayer.posX, par3EntityPlayer.posX, 4.0F, true);

			return super.onItemRightClick(par1ItemStack, par2World,
					par3EntityPlayer);
		}

		@Override
		public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
				Entity entity)
		{
			// TODO Auto-generated method stub
			if (player.getEntityWorld().isRemote)
				player.addChatMessage("");
			

			return super.onLeftClickEntity(stack, player, entity);
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		metadata = event.getModMetadata();
		myItem = new MyItem(1552).setUnlocalizedName("myItem");
		LanguageRegistry.addName(myItem, "KKKK");
	}

	@EventHandler
	public void load(FMLLoadCompleteEvent event)
	{
		LanguageRegistry.addName(myItem, "Kend");
		GameRegistry.registerItem(myItem, "KendItem", modId);
		GameRegistry.addRecipe(new ItemStack(myItem), " XX", 'x', Block.wood);
	}

	@EventHandler
	public void ServerStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandKSocket());
		event.registerServerCommand(new CommandKHome());
	}
}
