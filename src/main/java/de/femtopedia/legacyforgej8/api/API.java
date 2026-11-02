package de.femtopedia.legacyforgej8.api;

import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Provides useful API methods for injecting into Forge's/FML's ClassLoader ecosystem.
 */
public class API {

    private static final Set<String> classLoaderExceptions = new HashSet<>();
    private static final Set<String> transformerExceptions = new HashSet<>();

    private static final Set<Pattern> regexClassLoaderExceptions = new HashSet<>();
    private static final Set<Pattern> regexTransformerExceptions = new HashSet<>();

    static {
        // Add known candidates
        addClassLoaderExclusion("de.femtopedia.legacyforgej8."); // LegacyForgeJ8
        addTransformerExclusion("de.femtopedia.hufixes."); // HexxitUltimateFixes
        addTransformerExclusion("net.scoobis.nilmenu."); // NilMenu

        addRegexTransformerExclusion(Pattern.compile("de\\.femtopedia\\.nil.*fix\\..*")); // NilFix
    }

    /**
     * Use this to add your own ClassLoader exclusion rule to any
     * {@link RelaunchClassLoader} instance that is being loaded from now on.
     * The given string is being prefix-matched.
     * Classes that match prefixes in this exclusion list are loaded using
     * the parent ClassLoader instance of the {@link RelaunchClassLoader}.
     *
     * @param toExclude The ClassLoader exclusion rule to add.
     * @see RelaunchClassLoader#addClassLoaderExclusion(String)
     * @see API#addRegexClassLoaderExclusion(Pattern)
     */
    public static void addClassLoaderExclusion(String toExclude) {
        classLoaderExceptions.add(toExclude);
    }

    /**
     * Use this to add your own transformer exclusion rule to any
     * {@link RelaunchClassLoader} instance that is being loaded from now on.
     * The given string is being prefix-matched.
     * Classes that match prefixes in this exclusion list are not being
     * transformed using the registered transformers.
     *
     * @param toExclude The transformer exclusion rule to add.
     * @see RelaunchClassLoader#addTransformerExclusion(String)
     * @see API#addRegexTransformerExclusion(Pattern)
     */
    public static void addTransformerExclusion(String toExclude) {
        transformerExceptions.add(toExclude);
    }

    /**
     * Use this to add your own ClassLoader exclusion rule to any
     * {@link RelaunchClassLoader} instance that is being loaded from now on.
     * The given pattern is being regex-matched.
     * Classes that match regexes in this exclusion list are loaded using
     * the parent ClassLoader instance of the {@link RelaunchClassLoader}.
     *
     * @param toExclude The ClassLoader exclusion rule to add.
     * @see RelaunchClassLoader#addClassLoaderExclusion(String)
     * @see API#addClassLoaderExclusion(String)
     */
    public static void addRegexClassLoaderExclusion(Pattern toExclude) {
        regexClassLoaderExceptions.add(toExclude);
    }

    /**
     * Use this to add your own transformer exclusion rule to any
     * {@link RelaunchClassLoader} instance that is being loaded from now on.
     * The given pattern is being regex-matched.
     * Classes that match regexes in this exclusion list are not being
     * transformed using the registered transformers.
     *
     * @param toExclude The transformer exclusion rule to add.
     * @see RelaunchClassLoader#addTransformerExclusion(String)
     * @see API#addTransformerExclusion(String)
     */
    public static void addRegexTransformerExclusion(Pattern toExclude) {
        regexTransformerExceptions.add(toExclude);
    }

    /**
     * YOU PROBABLY DO NOT NEED TO USE THIS!
     * Registers the currently registered exceptions into the given
     * {@link RelaunchClassLoader} instance.
     *
     * @param rcl The {@link RelaunchClassLoader} instance to register
     *            the exceptions in.
     */
    public static void registerExclusions(RelaunchClassLoader rcl) {
        classLoaderExceptions.forEach(rcl::addClassLoaderExclusion);
        transformerExceptions.forEach(rcl::addTransformerExclusion);
    }

    /**
     * YOU PROBABLY DO NOT NEED TO USE THIS!
     * Checks whether the given class matches any of the currently
     * registered class loader exclusion regexes.
     *
     * @param className The class name to check.
     * @return {@code true} if the class matches any exclusion regex
     *         from {@link #regexClassLoaderExceptions},
     *         {@code false} otherwise.
     * @see API#addRegexClassLoaderExclusion(Pattern)
     */
    public static boolean matchesClassLoaderExclusionRegex(String className) {
        return regexClassLoaderExceptions.stream().anyMatch(p -> p.matcher(className).matches());
    }

    /**
     * YOU PROBABLY DO NOT NEED TO USE THIS!
     * Checks whether the given class matches any of the currently
     * registered transformer exclusion regexes.
     *
     * @param className The class name to check.
     * @return {@code true} if the class matches any exclusion regex
     *         from {@link #regexTransformerExceptions},
     *         {@code false} otherwise.
     * @see API#addRegexTransformerExclusion(Pattern)
     */
    public static boolean matchesTransformerExclusionRegex(String className) {
        return regexTransformerExceptions.stream().anyMatch(p -> p.matcher(className).matches());
    }

}
