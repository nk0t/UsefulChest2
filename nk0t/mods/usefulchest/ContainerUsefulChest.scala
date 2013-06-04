package nk0t.mods.usefulchest

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

class ContainerUsefulChest(inventory : IInventory,
                           tileEntityUsefulChest : TileEntityUsefulChest, page : Int)
        extends Container {

    val trashInventory = new InventoryTrash()
    val trashSlot = new Slot(trashInventory, 0, 226, 223)

    init()

    def init() = {

        tileEntityUsefulChest.openChest()

        val startSlot = (page - 1) * 104

        for (y <- 0 to 7; x <- 0 to 12) {
            addSlotToContainer(new Slot(tileEntityUsefulChest, startSlot + x + y * 13, 12 + x * 18, 17 + y * 18))
        }

        addSlotToContainer(trashSlot)

        for (y <- 0 to 2; x <- 0 to 8) {
            addSlotToContainer(new Slot(inventory, x + y * 9 + 9, 12 + x * 18, 165 + y * 18))
        }

        for (x <- 0 to 8) {
            addSlotToContainer(new Slot(inventory, x, 12 + x * 18, 223))
        }

    }

    override def canInteractWith(player : EntityPlayer) : Boolean = {

        return tileEntityUsefulChest.isUseableByPlayer(player)
    }

    override def transferStackInSlot(player : EntityPlayer, index : Int) : ItemStack = {

        var itemstack = null
        val slot = inventorySlots.get(index).asInstanceOf[Slot]
        if (slot != null && slot.getHasStack()) {
            val itemstack1 = slot.getStack()
            itemstack == itemstack1.copy()
            if (index < 104) {
                if (!mergeItemStack(itemstack1, 105, inventorySlots.size(), true)) {
                    return null
                }
            }
            else {
                if (!mergeItemStack(itemstack1, 0, 104, false)) {
                    return null
                }
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack(null)
            }
            else {
                slot.onSlotChanged()
            }
        }
        return itemstack
    }

    override def onCraftGuiClosed(player : EntityPlayer) = {
        tileEntityUsefulChest.closeChest()
    }

    def getChestInventory() = this.tileEntityUsefulChest
}