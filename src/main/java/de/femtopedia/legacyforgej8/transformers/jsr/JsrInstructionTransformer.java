package de.femtopedia.legacyforgej8.transformers.jsr;

import java.util.HashSet;
import java.util.Set;
import nilloader.api.ClassRetransformer;
import nilloader.api.NonLoadingClassWriter;
import nilloader.api.lib.asm.ClassReader;
import nilloader.api.lib.asm.ClassVisitor;
import nilloader.api.lib.asm.ClassWriter;
import nilloader.api.lib.asm.MethodVisitor;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.commons.JSRInlinerAdapter;

public class JsrInstructionTransformer implements ClassRetransformer {

    public static final Set<String> targets = new HashSet<>();

    static {
        targets.add("cpw/mods/fml/common/discovery/JarDiscoverer");
        targets.add("cpw/mods/fml/relauncher/RelaunchClassLoader");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, byte[] originalData) {
        if (!targets.contains(className)) return originalData;
        return applyJsrTransformation(loader, originalData);
    }

    @Override
    public byte[] transform(String className, byte[] originalData) {
        return originalData;
    }

    public static byte[] applyJsrTransformation(ClassLoader loader, byte[] originalData) {
        NonLoadingClassWriter cw = new NonLoadingClassWriter(loader, ClassWriter.COMPUTE_FRAMES);
        ClassReader cr = new ClassReader(originalData);
        cr.accept(new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new JSRInlinerAdapter(mv, access, name, descriptor, signature, exceptions);
            }
        }, ClassReader.EXPAND_FRAMES);
        return cw.toByteArray();
    }

    @Override
    public Set<String> getTargets() {
        return targets;
    }

}
