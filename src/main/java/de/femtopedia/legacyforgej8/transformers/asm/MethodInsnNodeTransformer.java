package de.femtopedia.legacyforgej8.transformers.asm;

import de.femtopedia.legacyforgej8.MiniPlusTransformer;
import nilloader.api.lib.asm.Opcodes;
import nilloader.api.lib.asm.tree.LabelNode;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("org.objectweb.asm.tree.MethodInsnNode")
public class MethodInsnNodeTransformer extends MiniPlusTransformer {

    @Patch.Method.AffectsControlFlow
    @Patch.Method("setOpcode(I)V")
    public void setItfValueDynamically(PatchContext ctx) {
        LabelNode L0 = new LabelNode();
        LabelNode Lcontinue = new LabelNode();

        ctx.jumpToStart();

        ctx.add(
                ALOAD(0),
                ILOAD(1),
                SIPUSH(Opcodes.INVOKEINTERFACE),
                IF_ICMPNE(L0),
                ICONST_1(),
                GOTO(Lcontinue),
                L0,
                ICONST_0(),
                Lcontinue,
                PUTFIELD("org/objectweb/asm/tree/MethodInsnNode", "itf", "Z")
        );
    }

}
