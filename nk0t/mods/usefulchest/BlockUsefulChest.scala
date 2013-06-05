package nk0t.mods.usefulchest

import scala.util.Random

import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.MathHelper
import net.minecraft.world.World

class BlockUsefulChest(blockId : Int) extends BlockContainer(blockId, Material.wood) {

    val random = new Random()

    this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F)
    this.setHardness(5.0f)
    this.setResistance(10.0f)
    this.setStepSound(Block.soundMetalFootstep)

    override def createNewTileEntity(world : World) : TileEntity = {

        var tileentitychest = new TileEntityUsefulChest();
        return tileentitychest;
    }

    override def onBlockPlacedBy(world : World, x : Int, y : Int, z : Int,
                                 entityLiving : EntityLiving, itemstack : ItemStack) = {

        var tileentity = world.getBlockTileEntity(x, y, z).asInstanceOf[TileEntityUsefulChest]
        var direction = MathHelper.floor_double(((entityLiving.rotationYaw * 4F) / 360F).asInstanceOf[Double] + 0.5D) & 3
        tileentity.Direction = direction.asInstanceOf[Byte]
    }

    override def isOpaqueCube() = false

    override def renderAsNormalBlock() = false

    override def getRenderType() = UsefulChest.usefulChestRendererId

    override def onBlockActivated(world : World, x : Int, y : Int, z : Int, player : EntityPlayer,
                                  s : Int, p : Float, q : Float, r : Float) : Boolean = {

        if (world.isRemote) {
            return true
        }
        else {
            player.openGui(UsefulChest.Instance, 0, world, x, y, z)
            return true
        }
    }

    override def breakBlock(world : World, i : Int, j : Int, k : Int, l : Int, m : Int) = {

        val tileentitychest = world.getBlockTileEntity(i, j, k).asInstanceOf[TileEntityUsefulChest]

        if (tileentitychest != null) {
            for (j1 <- 0 to tileentitychest.getSizeInventory - 1) {
                val itemstack = tileentitychest.getStackInSlot(j1)

                if (itemstack != null) {
                    val f = this.random.nextFloat() * 0.8F + 0.1F
                    val f1 = this.random.nextFloat() * 0.8F + 0.1F
                    val f2 = this.random.nextFloat() * 0.8F + 0.1F
                    var entityitem : EntityItem = null

                    while (itemstack.stackSize > 0) {

                        var k1 = this.random.nextInt(21) + 10

                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= k1;

                        entityitem = new EntityItem(world, (i.asInstanceOf[Float] + f).asInstanceOf[Double],
                            (j.asInstanceOf[Float] + f1).asInstanceOf[Double],
                            (k.asInstanceOf[Float] + f2).asInstanceOf[Double],
                            new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()))
                        val f3 = 0.05F;

                        entityitem.motionX = (this.random.nextGaussian().asInstanceOf[Float] * f3).asInstanceOf[Double];
                        entityitem.motionY = (this.random.nextGaussian().asInstanceOf[Float] * f3 + 0.2f).asInstanceOf[Double];
                        entityitem.motionZ = (this.random.nextGaussian().asInstanceOf[Float] * f3).asInstanceOf[Double];

                        if (itemstack.hasTagCompound()) {

                            entityitem.getEntityItem().setTagCompound(itemstack.getTagCompound().copy().asInstanceOf[NBTTagCompound]);
                        }

                        world.spawnEntityInWorld(entityitem)
                    }
                }
            }

            world.func_96440_m(i, j, k, l);
        }

        super.breakBlock(world, i, j, k, l, m)
    }

    override def registerIcons(register : IconRegister) = {
        this.blockIcon = register.registerIcon("blockDiamond")
    }
}