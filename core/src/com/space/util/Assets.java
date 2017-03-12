package com.space.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Assets.
 */
public class Assets {

	public static final String MODEL_EXTENSION = "g3dj";
	public static final String MODEL_FILE_PATH = "./bin/entities/";

	private AssetManager assets;

	public Assets() {
		assets = new AssetManager();
	}

	/**
	 * Load all 3d models
	 */
	public void loadModels() {
		for (String string : getFileNames())
			assets.load("entities/" + string, Model.class);
	}

	/**
	 * @param fileName
	 *            The assets filename with extension
	 * @return the asset
	 */
	public Model get(String fileName) {
		return assets.get("entities/" + fileName);
	}

	/**
	 * Updates the AssetManager, keeping it loading any assets in the preload queue.
	 * 
	 * @return true if all loading is finished.
	 */
	public boolean update() {
		return assets.update();
	}

	/*
	 * Returns a list of files in the MODEL_FILE_PATH directory
	 */
	private List<String> getFileNames() {
		List<String> fileNames = new ArrayList<String>();

		FileHandle directory = Gdx.files.internal(MODEL_FILE_PATH);
		iterateOverAssetsDirectory(directory, fileNames);

		return fileNames;
	}

	/*
	 * Recursively adds files to the file array if files have the MODEL_EXTENSION
	 */
	private void iterateOverAssetsDirectory(FileHandle begin, List<String> fileNames) {
		FileHandle[] handles = begin.list();

		for (FileHandle file : handles) {
			if (file.isDirectory()) {
				iterateOverAssetsDirectory(file, fileNames);
			} else {
				if (file.extension().equals(MODEL_EXTENSION))
					fileNames.add(file.name());
			}
		}
	}

}