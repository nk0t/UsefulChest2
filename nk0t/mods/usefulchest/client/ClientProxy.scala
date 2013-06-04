package nk0t.mods.usefulchest.client

import cpw.mods.fml.client.registry.ClientRegistry
import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import nk0t.mods.usefulchest.TileEntityUsefulChest
import nk0t.mods.usefulchest.common.CommonProxy

@SideOnly(Side.CLIENT)
class ClientProxy extends CommonProxy {

    override def registerRenderers() = {

        val usefulChestRenderer = new TileEntityUsefulChestRenderer()

        ClientRegistry.registerTileEntity(classOf[TileEntityUsefulChest], "UsefulChest", usefulChestRenderer)
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityUsefulChest], usefulChestRenderer)

        RenderingRegistry.registerBlockHandler(new ItemUsefulChestRenderer())
    }

}