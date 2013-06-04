package nk0t.mods.usefulchest.client

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.world.IBlockAccess
import nk0t.mods.usefulchest.TileEntityUsefulChest
import nk0t.mods.usefulchest.UsefulChest

@SideOnly(Side.CLIENT)
class ItemUsefulChestRenderer extends ISimpleBlockRenderingHandler {

    val chest = new TileEntityUsefulChest()

    override def renderInventoryBlock(block : Block, metadata : Int, modelID : Int, renderer : RenderBlocks) {
        TileEntityRenderer.instance.renderTileEntityAt(chest, 0d, 0d, 0d, 0f)
    }

    override def renderWorldBlock(world : IBlockAccess, x : Int, y : Int, z : Int, block : Block,
                                  modelId : Int, renderer : RenderBlocks) = false

    override def shouldRender3DInInventory() = true

    override def getRenderId() = UsefulChest.usefulChestRendererId
}
