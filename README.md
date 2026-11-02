# LegacyForgeJ8

Nilmod enabling Forge on Legacy Minecraft (1.3.2 up to 1.5.2) to load mods compiled with Java 8 properly.

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

## FAQ

**Q**: What the hell is a Nilmod? How do I install this? AAAAAAAAAAA??!?!?!!  
**A**: First of all: you can install NilLoader alongside any other loader (including Forge, ModLoader etc.)! A Nilmod is a mod that is loaded by [NilLoader](https://git.sleeping.town/Nil/NilLoader). You should [install NilLoader](https://git.sleeping.town/Nil/NilLoader#using-nilloader) first and then put LegacyForgeJ8 in either the usual `mods` folder or in the dedicated `nilmods` folder (recommended!) in your Minecraft instance.

**Q**: Is there a server version of this?  
**A**: The mod file _should_ work just fine on both client and server.

**Q**: What does this mod fix exactly?  
**A**: If you need to ask this question, this Nilmod will probably not do anything useful for you. To summarise, there is additional setup necessary for ASM to load mods compiled with Java 8. Forge did not follow these additional setup steps and hence, it can only load mods compiled with Java ≤ 7. This Nilmod lets Forge apply these additional setup steps and allows Forge to load such mods.

**Q**: Are there any plans for [any other Minecraft version]?  
**A**: This mod may work just fine for other MC versions as well. If not: let me know and I will try to provide a proper port!
