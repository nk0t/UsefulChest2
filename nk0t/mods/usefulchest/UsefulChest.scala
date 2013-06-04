package nk0t.mods.usefulchest

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.registry.LanguageRegistry
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraftforge.common.Configuration
import nk0t.mods.usefulchest.common.CommonProxy
import nk0t.mods.usefulchest.network.GuiHandler
import nk0t.mods.usefulchest.network.PacketHandler

@Mod(modid = "UsefulChest2", name = "UsefulChest2", version = "1.0.0", modLanguage = "scala")
@NetworkMod(
    clientSideRequired = true, serverSideRequired = true,
    channels = Array("UsefulChest2"), packetHandler = classOf[PacketHandler])
class UsefulChest {

    @Mod.PreInit
    def preInit(event : FMLPreInitializationEvent) {

        UsefulChest.Instance = this

        val config = new Configuration(event.getSuggestedConfigurationFile())
        UsefulChest.usefulChestBlockId = config.get( Configuration.CATEGORY_BLOCK, "UsefulChest_BlockID", 1300).getInt()
        config.save()

    }

    @Mod.Init()
    def init(event : FMLInitializationEvent) = {

        UsefulChest.usefulChestBlock = new BlockUsefulChest(UsefulChest.usefulChestBlockId)
        UsefulChest.usefulChestBlock.setUnlocalizedName("UsefulChest")
        UsefulChest.usefulChestBlock.setCreativeTab(CreativeTabs.tabDecorations)

        GameRegistry.registerBlock(UsefulChest.usefulChestBlock, "UsefulChest")
        LanguageRegistry.addName(UsefulChest.usefulChestBlock, "Useful Chest")
        LanguageRegistry.instance().addNameForObject(UsefulChest.usefulChestBlock, "ja_JP", "ユースフルチェスト")

        GameRegistry.addShapelessRecipe(new ItemStack(UsefulChest.usefulChestBlock, 64), Block.dirt)

        UsefulChest.proxy.registerRenderers()
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler())
    }

    @Mod.PostInit
    def postInit(event : FMLPostInitializationEvent) {

    }
}

object UsefulChest extends UsefulChest {

    var Instance : UsefulChest = null

    val PacketChannel = "UsefulChest2"

    @SidedProxy(
        clientSide = "nk0t.mods.usefulchest.client.ClientProxy",
        serverSide = "nk0t.mods.usefulchest.common.CommonProxy")
    var proxy : CommonProxy = null

    val usefulChestRendererId = RenderingRegistry.getNextAvailableRenderId()

    var usefulChestBlock : Block = null

    var usefulChestBlockId = 0

}
