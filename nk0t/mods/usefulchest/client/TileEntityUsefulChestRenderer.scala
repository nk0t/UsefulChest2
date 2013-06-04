package nk0t.mods.usefulchest.client

import org.lwjgl.opengl.GL11

import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.model.ModelChest
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import nk0t.mods.usefulchest.TileEntityUsefulChest

@SideOnly(Side.CLIENT)
class TileEntityUsefulChestRenderer extends TileEntitySpecialRenderer {

    var chestModel = new ModelChest()

    override def renderTileEntityAt(tileentity : TileEntity, x : Double, y : Double, z : Double, f : Float) = {
        this.renderTileEntityAt(tileentity.asInstanceOf[TileEntityUsefulChest], x, y, z, f)
    }

    def renderTileEntityAt(tileEntity : TileEntityUsefulChest, x : Double, y : Double, z : Double, f : Float) = {

        val direction = tileEntity.Direction

        this.bindTextureByName("/nk0t/mods/usefulchest/resouces/usefulchest.png");

        GL11.glPushMatrix()
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        GL11.glTranslatef(x.asInstanceOf[Float], y.asInstanceOf[Float] + 1.0F, z.asInstanceOf[Float] + 1.0F)
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        val angle = direction match {
            case 0 => 180f
            case 1 => -90f
            case 2 => 0f
            case 3 => 90f
        }

        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F)
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F)

        var f1 = tileEntity.prevLidAngle + (tileEntity.lidAngle - tileEntity.prevLidAngle) * f;
        f1 = 1.0F - f1;
        f1 = 1.0F - f1 * f1 * f1
        chestModel.chestLid.rotateAngleX = -(f1 * math.Pi.asInstanceOf[Float] / 2.0F);

        chestModel.renderAll();
        GL11.glPopMatrix()
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
    }
}