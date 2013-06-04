package nk0t.mods.usefulchest.network

import java.io.IOException

import com.google.common.io.ByteStreams

import cpw.mods.fml.common.network.IPacketHandler
import cpw.mods.fml.common.network.Player
import cpw.mods.fml.relauncher.Side
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.Packet250CustomPayload
import nk0t.mods.usefulchest.TileEntityUsefulChest
import nk0t.mods.usefulchest.UsefulChest
import nk0t.mods.usefulchest.UsefulChestNBT

class PacketHandler() extends IPacketHandler {

    override def onPacketData(manager : INetworkManager,
                              packet : Packet250CustomPayload,
                              player : Player) = {

        if (packet.channel == UsefulChest.PacketChannel) {

            val data = ByteStreams.newDataInput(packet.data)

            val nbt : NBTTagCompound = try {
                NBTBase.readNamedTag(data).asInstanceOf[NBTTagCompound]
            }
            catch {
                case e : IOException => {
                    e.printStackTrace()
                    null
                }
            }

            if (nbt != null) {
                readNBT(nbt, player.asInstanceOf[EntityPlayer])
            }
        }

    }

    def readNBT(nbt : NBTTagCompound, player : EntityPlayer) = {

        var tileentity = player.worldObj.getBlockTileEntity(
            nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"))

        if (tileentity.isInstanceOf[TileEntityUsefulChest]) {

            val chest = tileentity.asInstanceOf[TileEntityUsefulChest]

            nbt.getString("Side") match {

                case PacketHandler.Client => {
                    chest.Direction = nbt.getByte(UsefulChestNBT.Direction)
                    chest.Page = nbt.getInteger(UsefulChestNBT.Page)
                }

                case PacketHandler.Server => {
                    nbt.getString(UsefulChestNBT.Mode) match {

                        case UsefulChestNBT.Page => {
                            chest.Page = nbt.getInteger(UsefulChestNBT.Page)
                            player.openGui(UsefulChest.Instance, 0, player.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord)
                        }

                        case UsefulChestNBT.Sort=>{
                            chest.sortChest()
                        }
                    }
                }

            }
        }
    }
}

object PacketHandler extends PacketHandler {

    val Client = "UsefulChestClient"
    val Server = "UsefulChestServer"

    def createPacket(nbt : NBTTagCompound) : Packet = {
        val data = ByteStreams.newDataOutput()
        NBTBase.writeNamedTag(nbt, data)
        return new Packet250CustomPayload(UsefulChest.PacketChannel, data.toByteArray())
    }

    def createNBTBase(side : Side) : NBTTagCompound = {
        val nbt = new NBTTagCompound()
        side match {
            case Side.CLIENT => {
                nbt.setString("Side", PacketHandler.Client)
            }
            case Side.SERVER => {
                nbt.setString("Side", PacketHandler.Server)
            }
            case _ => {}
        }
        return nbt
    }

}
