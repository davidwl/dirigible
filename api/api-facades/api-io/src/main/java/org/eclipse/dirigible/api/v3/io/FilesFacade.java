/*
 * Copyright (c) 2017 SAP and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * SAP - initial API and implementation
 */

package org.eclipse.dirigible.api.v3.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

import org.eclipse.dirigible.commons.api.helpers.BytesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Facade for working with files
 */
public class FilesFacade {
	
	private static final Logger logger = LoggerFactory.getLogger(FilesFacade.class);

	/**
	 * Check if file with the provided path exists
	 * @param path the path to the file
	 * @return true if a file exists
	 * @throws IOException
	 */
	public static final boolean exists(String path) throws IOException {
//		return Files.exists(Paths.get(path));
		return Paths.get(path).toFile().exists();
	}

	/**
	 * Check if file with the provided path is executable
	 * @param path the path to the file
	 * @return true if the file is executable
	 * @throws IOException
	 */
	public static final boolean isExecutable(String path) throws IOException {
		return Files.isExecutable(Paths.get(path));
	}

	/**
	 * Check if file with the provided path is readable
	 * @param path the path to the file
	 * @return true if the file is readable
	 * @throws IOException
	 */
	public static final boolean isReadable(String path) throws IOException {
		return Files.isReadable(Paths.get(path));
	}

	/**
	 * Check if file with the provided path is writable
	 * @param path the path to the file
	 * @return true if the file is writable
	 * @throws IOException
	 */
	public static final boolean isWritable(String path) throws IOException {
		return Files.isReadable(Paths.get(path));
	}

	/**
	 * Check if file with the provided path is hidden
	 * @param path the path to the file
	 * @return true if the file is hidden
	 * @throws IOException
	 */
	public static final boolean isHidden(String path) throws IOException {
		return Files.isHidden(Paths.get(path));
	}

	/**
	 * Check if file with the provided path is a directory
	 * @param path the path to the file
	 * @return true if the file is directory
	 * @throws IOException
	 */
	public static final boolean isDirectory(String path) throws IOException {
		return Files.isDirectory(Paths.get(path));
	}

	/**
	 * Check if file with the provided path is a regular file
	 * @param path the path to the file
	 * @return true if the file is a regular file
	 * @throws IOException
	 */
	public static final boolean isFile(String path) throws IOException {
		return Files.isRegularFile(Paths.get(path));
	}

	/**
	 * Check if the two paths point to the same file
	 * @param path1 path to the first file
	 * @param path2 path to the second file
	 * @return true if both paths point to the same file
	 * @throws IOException
	 */
	public static final boolean isSameFile(String path1, String path2) throws IOException {
		return Files.isSameFile(Paths.get(path1), Paths.get(path2));
	}

	/**
	 * Get canonical representation of the provided path
	 * @param path the path
	 * @return the canonical representation of the path
	 * @throws IOException
	 */
	public static final String getCanonicalPath(String path) throws IOException {
		return new File(path).getCanonicalPath();
	}

	/**
	 * Get the name of the file or directory represented by the provided path
	 * @param path the path
	 * @return the name of the file or the directory
	 * @throws IOException
	 */
	public static final String getName(String path) throws IOException {
		return new File(path).getName();
	}

	/**
	 * Get the path of the parentdirectory
	 * @param path the path
	 * @return the path of the parent
	 * @throws IOException
	 */
	public static final String getParentPath(String path) throws IOException {
		return new File(path).getParentFile().getPath(); //FIXME may throw NPE
	}

	/**
	 * Read the content of the file into a byte array
	 * @param path the path of the file
	 * @return the file content as byte array
	 * @throws IOException
	 */
	public static final byte[] readBytes(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

	/**
	 * Read the text file represented by the provided path using UTF-8 charset
	 * @param path path to the file
	 * @return the text file content
	 * @throws IOException
	 */
	public static final String readText(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	}

	/**
	 * Write the provided data to the file represented by the provided path.
	 * If the file exists the old content is discarded.
	 * @param path path to the file to write to
	 * @param input the data to write
	 * @throws IOException
	 */
	public static final void writeBytes(String path, String input) throws IOException {
		byte[] bytes = BytesHelper.jsonToBytes(input);
		Files.write(Paths.get(path), bytes);
	}

	/**
	 * Write text to the file represented by the provided path using UTF-8 charset.
	 * If the file exists the old content is discarded.
	 * @param path path to the file to write to
	 * @param text the data to write
	 * @throws IOException
	 */	public static final void writeText(String path, String text) throws IOException {
		Files.write(Paths.get(path), text.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Get the timestamp of last modification
	 * @param path path to the file
	 * @return the timestamp of last modification
	 * @throws IOException
	 */
	public static final long getLastModified(String path) throws IOException {
		return Files.getLastModifiedTime(Paths.get(path)).toMillis();
	}

	/**
	 * Set the last modification timestamp
	 * @param path path to a file
	 * @param time the last modification timestamp to set
	 * @throws IOException
	 */
	public static final void setLastModified(String path, long time) throws IOException {
		Files.setLastModifiedTime(Paths.get(path), FileTime.fromMillis(time));
	}

	/**
	 * Set the last modification timestamp
	 * @param path path to a file
	 * @param time the last modification timestamp to set
	 * @throws IOException
	 */
	public static final void setLastModified(String path, Double time) throws IOException {
		setLastModified(path, time.longValue());
	}

	/**
	 * Get the name of teh principal representing the file owner
	 * @param path path to a file
	 * @return the name of the principal representing the file owner
	 * @throws IOException
	 */
	public static final String getOwner(String path) throws IOException {
		return Files.getOwner(Paths.get(path)).getName();
	}

	/**
	 * Set the file owner
	 * @param path path to file
	 * @return the name of the principal representing the file owner
	 * @throws IOException
	 */
	public static final void setOwner(String path, String owner) throws IOException {
		UserPrincipal userPrincipal = Paths.get(path).getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(owner);
		Files.setOwner(Paths.get(path), userPrincipal);
	}

	/**
	 * Get the string representation of the file permissions
	 * @param path path to the file
	 * @return the string representation of the file permissions
	 * @throws IOException
	 */
	public static final String getPermissions(String path) throws IOException {
		return PosixFilePermissions.toString(Files.getPosixFilePermissions(Paths.get(path)));
	}

	/**
	 * Set the file permissions
	 * @param path path to the file
	 * @return the string representation of the file permissions
	 * @throws IOException
	 */
	public static final void setPermissions(String path, String permissions) throws IOException {
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString(permissions);
	    Files.setPosixFilePermissions(Paths.get(path), perms);
	}

	/**
	 * Get the file size
	 * @param path path to the file
	 * @return the file size
	 * @throws IOException
	 */
	public static final long size(String path) throws IOException {
		return Files.size(Paths.get(path));
	}

	/**
	 * Create a new file if it does not exist
	 * @param path path to the file
	 * @throws IOException
	 */
	public static final void createFile(String path) throws IOException {
		Files.createFile(Paths.get(path));
	}

	/**
	 * Create all directories on the given path if they do not exist
	 * @param path
	 * @throws IOException
	 */
	public static final void createDirectory(String path) throws IOException {
		Files.createDirectories(Paths.get(path));
	}

	/**
	 * Copy the directory structure
	 * @param source source location
	 * @param target target location
	 * @throws IOException
	 */
	public static final void copy(String source, String target) throws IOException {
		Path sourcePath = Paths.get(source);
		Path targetPath = Paths.get(target);
		Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
				Files.createDirectories(targetPath.resolve(sourcePath.relativize(dir)));
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				Files.copy(file, targetPath.resolve(sourcePath.relativize(file)));
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Move the file to the specified location
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static final void move(String source, String target) throws IOException {
		Files.move(Paths.get(source), Paths.get(target));
	}

	/**
	 * Delete the file if it exists
	 * @param path path to file
	 * @throws IOException
	 */
	public static final void deleteFile(String path) throws IOException {
		Files.deleteIfExists(Paths.get(path));
	}

	/**
	 * Delete empty directory
	 * @param path path to the directory
	 * @throws IOException
	 */
	public static final void deleteDirectory(String path) throws IOException {
		Files.deleteIfExists(Paths.get(path));
	}

	/**
	 * Delete directory. Set foce to true in order to delete non empty directory
	 * @param path path to the directory
	 * @param forced should non-empty directories be deleted
	 * @throws IOException
	 */
	public static final void deleteDirectory(String path, boolean forced) throws IOException {
		if (forced) {
			Files.walkFileTree(Paths.get(path), new FileVisitor<Path>() {
	
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
	
//					if (Files.exists(dir)) {
					if (dir.toFile().exists()) {
						logger.trace(String.format("Deleting directory: %s", dir));
						try {
							Files.delete(dir);
						} catch (java.nio.file.NoSuchFileException e) {
							logger.trace(String.format("Directory already has been deleted: %s", dir));
						}
					}
					return FileVisitResult.CONTINUE;
				}
	
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					return FileVisitResult.CONTINUE;
				}
	
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//					if (Files.exists(file)) {
					if (file.toFile().exists()) {
						logger.trace(String.format("Deleting file: %s", file));
						try {
							Files.delete(file);
						} catch (java.nio.file.NoSuchFileException e) {
							logger.trace(String.format("File already has been deleted: %s", file));
						}
					}
					return FileVisitResult.CONTINUE;
				}
	
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					logger.error(String.format("Error in file: %s", file), exc);
					return FileVisitResult.CONTINUE;
				}
			});
		} else {
			Files.deleteIfExists(Paths.get(path));
		}
	}

	/**
	 * Create temporary file
	 * @param prefix
	 * @param suffix
	 * @return path to the temp file
	 * @throws IOException
	 */
	public static final String createTempFile(String prefix, String suffix) throws IOException {
		return Files.createTempFile(prefix, suffix).toString();
	}

	/**
	 * Create temporary directory
	 * @param prefix
	 * @return path to the created directory
	 * @throws IOException
	 */
	public static final String createTempDirectory(String prefix) throws IOException {
		return Files.createTempDirectory(prefix).toString();
	}


	/**
	 * Open input stream from the file represented by the given path
	 * @param path path to file
	 * @return the created input stream
	 * @throws IOException
	 */
	public static final InputStream createInputStream(String path) throws IOException {
		return Files.newInputStream(Paths.get(path));
	}

	/**
	 * Open output stream  to the file represented by the given path
	 * @param path path to file
	 * @return the created output stream
	 * @throws IOException
	 */
	public static final OutputStream createOutputStream(String path) throws IOException {
		return Files.newOutputStream(Paths.get(path));
	}

}
