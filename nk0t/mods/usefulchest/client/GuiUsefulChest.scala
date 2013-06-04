package nk0t.mods.usefulchest.client

import java.util.ArrayList

import org.lwjgl.opengl.GL11

import cpw.mods.fml.common.network.PacketDispatcher
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.IInventory
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.StringTranslate
import net.minecraft.world.World
import nk0t.mods.usefulchest.ContainerUsefulChest
import nk0t.mods.usefulchest.TileEntityUsefulChest
import nk0t.mods.usefulchest.UsefulChestNBT
import nk0t.mods.usefulchest.network.PacketHandler

@SideOnly(Side.CLIENT)
class GuiUsefulChest(inventory : IInventory, tileEntityUsefulChest : TileEntityUsefulChest,
                     var page : Int, world : World, x : Int, y : Int, z : Int)
        extends GuiContainer(new ContainerUsefulChest(inventory, tileEntityUsefulChest, page)) {

    val PrevButtonID = 400
    val NextButtonID = 401
    val SortButtonID = 402
    val pageMax = 10

    this.xSize = 256
    this.ySize = 256
    this.tileEntityUsefulChest.Page = page

    val trashSlot = this.inventorySlots.asInstanceOf[ContainerUsefulChest].trashSlot

    override def initGui() = {
        super.initGui()

        val x = (width - xSize) / 2;
        val y = (height - ySize) / 2;
        this.buttonList.clear()
        val prevButton = new GuiButton(PrevButtonID, (x + 195) - 15, y + 175, 15, 20, "<");
        val nextButton = new GuiButton(NextButtonID, x + 195, y + 175, 15, 20, ">");
        val sortButton = new GuiButton(SortButtonID, x + 220, y + 175, 27, 20, "Sort");

        this.buttonList.asInstanceOf[ArrayList[GuiButton]].add(prevButton)
        this.buttonList.asInstanceOf[ArrayList[GuiButton]].add(nextButton)
        this.buttonList.asInstanceOf[ArrayList[GuiButton]].add(sortButton)
    }

    override def drawGuiContainerForegroundLayer(par1 : Int, par2 : Int) = {

        fontRenderer.drawString(page.toString(), 180, 165, 0x404040)
        fontRenderer.drawString("/", 195, 165, 0x404040)
        fontRenderer.drawString(pageMax.toString(), 205, 165, 0x404040)
    }

    override def drawGuiContainerBackgroundLayer(f : Float, i : Int, j : Int) = {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        mc.renderEngine.bindTexture("/nk0t/mods/usefulchest/resouces/usefulchestContainer.png")
        val x = (width - xSize) / 2
        val y = (height - ySize) / 2
        drawTexturedModalRect(x, y + 9, 0, 0, xSize, ySize - 18)
    }

    override def actionPerformed(guiButton : GuiButton) = {

        guiButton.id match {

            case PrevButtonID | NextButtonID => {

                if (guiButton.id == PrevButtonID) {
                    if (this.page > 1) {
                        this.page -= 1
                    }
                    else {
                        this.page = pageMax
                    }
                }
                else if (guiButton.id == NextButtonID) {
                    if (this.page < this.pageMax) {
                        this.page += 1
                    }
                    else {
                        this.page = 1
                    }
                }

                val nbt = createSendNBTBase()
                nbt.setString(UsefulChestNBT.Mode, UsefulChestNBT.Page)
                nbt.setInteger(UsefulChestNBT.Page, this.page)
                tileEntityUsefulChest.Page = this.page
                PacketDispatcher.sendPacketToServer(PacketHandler.createPacket(nbt))
            }

            case SortButtonID => {

                tileEntityUsefulChest.sortChest()

                val nbt = createSendNBTBase()
                nbt.setString(UsefulChestNBT.Mode, UsefulChestNBT.Sort)
                PacketDispatcher.sendPacketToServer(PacketHandler.createPacket(nbt))
            }
        }

    }

    override def drawScreen(x : Int, y : Int, k : Float) = {
        super.drawScreen(x, y, k)

        if (this.isPointInRegion(trashSlot.xDisplayPosition, trashSlot.yDisplayPosition,
            16, 16, x, y)) {
            this.drawCreativeTabHoveringText(
                StringTranslate.getInstance().translateKey("inventory.binSlot"),
                x, y);
        }
    }

    def createSendNBTBase() : NBTTagCompound = {
        val nbt = PacketHandler.createNBTBase(Side.SERVER)
        nbt.setInteger("x", x)
        nbt.setInteger("y", y)
        nbt.setInteger("z", z)
        return nbt
    }

}
