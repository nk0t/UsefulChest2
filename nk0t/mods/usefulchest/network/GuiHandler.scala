package nk0t.mods.usefulchest.network

import cpw.mods.fml.common.network.IGuiHandler
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import nk0t.mods.usefulchest.ContainerUsefulChest
import nk0t.mods.usefulchest.TileEntityUsefulChest
import nk0t.mods.usefulchest.client.GuiUsefulChest

class GuiHandler extends IGuiHandler {

    override def getServerGuiElement(ID : Int, player : EntityPlayer,
                                     world : World, x : Int, y : Int, z : Int) : Object = {

        val tileEntity = world.getBlockTileEntity(x, y, z)

        if (tileEntity.isInstanceOf[TileEntityUsefulChest]) {

            val usefulChest = tileEntity.asInstanceOf[TileEntityUsefulChest]
            return new ContainerUsefulChest(player.inventory, usefulChest, usefulChest.Page)
        }
        return null
    }

    override def getClientGuiElement(ID : Int, player : EntityPlayer,
                                     world : World, x : Int, y : Int, z : Int) : Object = {

        val tileEntity = world.getBlockTileEntity(x, y, z)

        if (tileEntity.isInstanceOf[TileEntityUsefulChest]) {

            val usefulChest = tileEntity.asInstanceOf[TileEntityUsefulChest]
            return new GuiUsefulChest(player.inventory, usefulChest, usefulChest.Page, world, x, y, z)
        }
        return null
    }

}