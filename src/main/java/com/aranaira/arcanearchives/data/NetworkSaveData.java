//Some credit goes to the developers of Blood Magic as I am using their codebase as an example of how to do certain things.
//Their WorldSavedData https://github.com/WayofTime/BloodMagic/blob/c8e42e32885e40ac17d05c7af35d4299d9e74dbe/src/main/java/WayofTime/bloodmagic/core/data/BMWorldSavedData.java

package com.aranaira.arcanearchives.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NetworkSaveData extends WorldSavedData {
	public static final String ID = "Archane-Archives-Network";

	private Map<UUID, ServerNetwork> arcaneArchivesNetworks = new HashMap<>();

	// It REALLY is used.
	@SuppressWarnings("unused")
	public NetworkSaveData (String id) {
		super(id);
	}

	public NetworkSaveData () {
		super(ID);
	}

	// TODO: Use this?
	public void clearServerMap () {
		arcaneArchivesNetworks.clear();
	}

	public Collection<ServerNetwork> getAllNetworks () {
		return arcaneArchivesNetworks.values();
	}

	@Nullable
	public ServerNetwork getNetwork (@Nullable UUID playerID) {
		if (playerID == null || playerID == DataHelper.INVALID) {
			return null;
		}

		if (!arcaneArchivesNetworks.containsKey(playerID)) {
			arcaneArchivesNetworks.put(playerID, new ServerNetwork(playerID));
			this.markDirty();
		}
		return arcaneArchivesNetworks.get(playerID);
	}

	@Override
	public void readFromNBT (NBTTagCompound tagCompound) {
		NBTTagList networkData = tagCompound.getTagList("networkData", 10);

		for (int i = 0; i < networkData.tagCount(); i++) {
			NBTTagCompound data = networkData.getCompoundTagAt(i);
			ServerNetwork network = ServerNetwork.fromNBT(data);
			arcaneArchivesNetworks.put(network.getUuid(), network);
		}
	}

	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound tagCompound) {
		NBTTagList networkData = new NBTTagList();

		for (ServerNetwork network : arcaneArchivesNetworks.values()) {
			networkData.appendTag(network.writeToSave());
		}

		tagCompound.setTag("networkData", networkData);

		return tagCompound;
	}
}
