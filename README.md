# LegacyForgeJ8

Nilmod enabling Forge on Legacy Minecraft (1.3.2 up to 1.7.10) to load mods compiled with Java 8 properly.

## Current fixes

- Update everything to use `ASM5` API
- Allow Java 8 in MCPC+
- Exclude certain Java 9+ class files from loading (fixes crashes and errors)
- Update to ASM 5.2 (from 4.0; provides proper Java 8 support)
- Patch known mods to support ASM 5
- Add own API for excluding classes from transformer and class loader
<!-- Not anymore: - Update BouncyCastle to 1.69 (from 1.47; fixes many vulnerabilities) -->
- Add Gson as an additional library (does not make anything worse, but may be nice to use for some legacy modders as a dependency that is now built-in)
- Download libraries and dependencies from an actively working mirror
- Increase hardcoded download limits
- Fix a few other probable crashes with unknown/invalid class files

## Installing in Minecraft 1.6+

In Minecraft 1.6, library management has changed with the new launcher. For this reason, LegacyForgeJ8 cannot just replace the libraries in runtime. It needs your help. **Older versions of Minecraft do not need this special treatment!**  
For this, read the following additional setup instructions carefully. Depending on which launcher you use for Minecraft (or whether you want to use the mod on a server), the instructions will differ a bit. I highly recommend Prism Launcher since it is much easier to deal with than the clunky Vanilla Launcher.

### Prism and other MultiMC-like launchers

1. Select your Minecraft instance.
2. Click on "Edit" in the sidebar on the right. A window should appear.
3. Click on "Version" in the sidebar on the left.
4. In the list, select "Forge" and click on "Customize", then "Edit" in the sidebar on the right. An editor should open.
5. Search for a line similar to `"name": "org.ow2.asm:asm-all:4.1"`.
6. Replace the `4.1` by `5.2`. No need to replace it, if it is already some version starting with a `5` such as `5.0.3`.
7. You can now close the editor and launch the Minecraft instance.

### Vanilla launcher

1. Play the release you want to install LegacyForgeJ8 into at least once and install NilLoader, Forge and any other prerequisites BEFORE continuing here.
2. Go to your `.minecraft` folder. Since there are so many ways to find it, please search for ways to find it yourself.
3. In the `.minecraft` folder, go into the `versions` folder. After installing Forge, you should have a folder such as `1.6.4-Forge9.11.1.1345` in there. Go into that one.
4. You should see two files. One of them has the file extension `.json`. Open that one in an editor.
5. Search for a line similar to `"name": "org.ow2.asm:asm-all:4.1"`.
6. Replace the `4.1` (or similar version) by `5.2`.
7. You can now close the editor. If your Minecraft launcher is still open, you need to close it as well and re-open it.
8. In your `.minecraft` folder, navigate to `libraries/org/ow2/asm/asm-all` and create a folder called `5.2` there.
9. Download [`asm-all-5.2.jar`](https://repo1.maven.org/maven2/org/ow2/asm/asm-all/5.2/asm-all-5.2.jar) and put it into this folder.
10. You can now launch the Minecraft instance.

### Servers

You need to find the version `json` file. Most likely, it is located within the Forge server jar file. You can open it using any program that can open zip files (such as WinRAR or PeaZip) and search for the version `json` file in there. Once you found it, you can follow the instructions for the Vanilla launcher from step 5. Instead of the `.minecraft` directory, look in your server directory.  
If you are using KCauldron, Cauldron, MCPC+ or similar "SpigotForge" variants, the version `json` file may be even more hidden in some jar file within the `libraries` folder, `bin` folder or somewhere different. Depending on the variant, you need to put the `asm-all-5.2.jar` file also somewhere different.

## FAQ

**Q**: What the hell is a Nilmod? How do I install this? AAAAAAAAAAA??!?!?!!  
**A**: First of all: you can install NilLoader alongside any other loader (including Forge, ModLoader etc.)! A Nilmod is a mod that is loaded by [NilLoader](https://git.sleeping.town/Nil/NilLoader). You should [install NilLoader](https://git.sleeping.town/Nil/NilLoader#using-nilloader) first and then put LegacyForgeJ8 in either the usual `mods` folder or in the dedicated `nilmods` folder (recommended!) in your Minecraft instance. If you intend to use LegacyForgeJ8 for Minecraft 1.6 or newer, follow the additional setup instructions above as well.

**Q**: Is there a server version of this?  
**A**: The mod file _should_ work just fine on both client and server.

**Q**: What does this mod fix exactly?  
**A**: If you need to ask this question, this Nilmod will probably not do anything useful for you. To summarise, there is additional setup necessary for ASM to load mods compiled with Java 8. Forge did not follow these additional setup steps and hence, it can only load mods compiled with Java ≤ 7. This Nilmod lets Forge apply these additional setup steps and allows Forge to load such mods.

**Q**: Are there any plans for [any other Minecraft version]?  
**A**: This mod may work just fine for other MC versions as well. If not: let me know and I will try to provide a proper port!
