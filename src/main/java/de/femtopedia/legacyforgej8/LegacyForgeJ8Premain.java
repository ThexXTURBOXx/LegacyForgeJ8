package de.femtopedia.legacyforgej8;

import de.femtopedia.legacyforgej8.transformers.api.RelaunchClassLoaderTransformer;
import de.femtopedia.legacyforgej8.transformers.asm.MethodInsnNodeTransformer;
import de.femtopedia.legacyforgej8.transformers.java9.JarDiscovererTransformer;
import de.femtopedia.legacyforgej8.transformers.libs.CoreFMLLibrariesTransformer;
import de.femtopedia.legacyforgej8.transformers.libs.RelaunchLibraryManagerTransformer;
import de.femtopedia.legacyforgej8.transformers.mcpc.FMLRelauncherTransformer;
import de.femtopedia.legacyforgej8.transformers.miscperipherals.BlockTurtleTransformerTransformer;
import de.femtopedia.legacyforgej8.transformers.miscperipherals.TileEntityTurtleTransformerTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.ASMTransformerTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.EventTransformerTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.MLDetectorClassVisitorTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.ModAnnotationVisitorTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.ModClassVisitorTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.ModFieldVisitorTransformer;
import de.femtopedia.legacyforgej8.transformers.opcodes.ModMethodVisitorTransformer;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class LegacyForgeJ8Premain implements Runnable {

    public static final NilLogger log = NilLogger.get("LegacyForgeJ8");

    @Override
    public void run() {
        ClassTransformer.register(new CoreFMLLibrariesTransformer());
        ClassTransformer.register(new RelaunchLibraryManagerTransformer());

        ClassTransformer.register(new ASMTransformerTransformer());
        ClassTransformer.register(new EventTransformerTransformer());
        ClassTransformer.register(new MLDetectorClassVisitorTransformer());

        ClassTransformer.register(new ModAnnotationVisitorTransformer());
        ClassTransformer.register(new ModClassVisitorTransformer());
        ClassTransformer.register(new ModFieldVisitorTransformer());
        ClassTransformer.register(new ModMethodVisitorTransformer());

        ClassTransformer.register(new FMLRelauncherTransformer());

        log.info("Java 8 mods are now supported!");

        ClassTransformer.register(new JarDiscovererTransformer());

        log.info("Skipping some unsupported Java 9+ entries...");

        ClassTransformer.register(new BlockTurtleTransformerTransformer());
        ClassTransformer.register(new TileEntityTurtleTransformerTransformer());

        log.info("Patched mods still relying on ASM 4.");

        ClassTransformer.register(new RelaunchClassLoaderTransformer());

        log.info("Injected API for registering own exclusions.");

        ClassTransformer.register(new MethodInsnNodeTransformer());

        log.info("Patched ASM itself just to be safe.");
    }

}
