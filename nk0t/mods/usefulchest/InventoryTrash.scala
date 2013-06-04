package nk0t.mods.usefulchest

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

class InventoryTrash extends IInventory {

    override def getSizeInventory = 1

    override def getStackInSlot(index : Int) = null

    override def decrStackSize(index : Int, size : Int) = null

    override def getStackInSlotOnClosing(index : Int) = null

    override def setInventorySlotContents(index : Int, itemstack : ItemStack) = {}

    override def getInvName() = "Trash"

    override def isInvNameLocalized() = false

    override def getInventoryStackLimit() = 64

    override def onInventoryChanged() = {}

    override def isUseableByPlayer(entityplayer : EntityPlayer) = true

    override def openChest() = {}

    override def closeChest() = {}

    override def isStackValidForSlot(i : Int, itemstack : ItemStack) = true
}
