/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package RestTemplateAddonUserSystemProxy;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class RestTemplateRewriteTest {
    @Test
    public void test() {
        Instrumentation instrument = new Instrumentation() {
            @Override
            public void addTransformer(ClassFileTransformer transformer, boolean canRetransform) {

            }

            @Override
            public void addTransformer(ClassFileTransformer transformer) {

            }

            @Override
            public boolean removeTransformer(ClassFileTransformer transformer) {
                return false;
            }

            @Override
            public boolean isRetransformClassesSupported() {
                return false;
            }

            @Override
            public void retransformClasses(Class<?>... classes) throws UnmodifiableClassException {

            }

            @Override
            public boolean isRedefineClassesSupported() {
                return false;
            }

            @Override
            public void redefineClasses(ClassDefinition... definitions) throws ClassNotFoundException, UnmodifiableClassException {

            }

            @Override
            public boolean isModifiableClass(Class<?> theClass) {
                return false;
            }

            @Override
            public Class[] getAllLoadedClasses() {
                return new Class[]{RestTemplate.class};
            }

            @Override
            public Class[] getInitiatedClasses(ClassLoader loader) {
                return new Class[]{RestTemplate.class};
            }

            @Override
            public long getObjectSize(Object objectToSize) {
                return 1;
            }

            @Override
            public void appendToBootstrapClassLoaderSearch(JarFile jarfile) {

            }

            @Override
            public void appendToSystemClassLoaderSearch(JarFile jarfile) {

            }

            @Override
            public boolean isNativeMethodPrefixSupported() {
                return false;
            }

            @Override
            public void setNativeMethodPrefix(ClassFileTransformer transformer, String prefix) {

            }

            @Override
            public void redefineModule(Module module, Set<Module> extraReads, Map<String, Set<Module>> extraExports, Map<String, Set<Module>> extraOpens, Set<Class<?>> extraUses, Map<Class<?>, List<Class<?>>> extraProvides) {

            }

            @Override
            public boolean isModifiableModule(Module module) {
                return false;
            }
        };
        RestTemplateRewrite.premain(null, instrument);

        RestTemplate restTemplate = new RestTemplate();
        Field requestFactoryField = ReflectionUtils.findField(
                restTemplate.getClass(), "requestFactory");
        requestFactoryField.setAccessible(true);
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
                (HttpComponentsClientHttpRequestFactory) ReflectionUtils.getField(
                        requestFactoryField, restTemplate);

        Field httpClientField = ReflectionUtils.findField(HttpComponentsClientHttpRequestFactory.class, "httpClient");
        httpClientField.setAccessible(true);

        HttpClient httpClient = (HttpClient) ReflectionUtils.getField(httpClientField, httpComponentsClientHttpRequestFactory);

        SystemDefaultCredentialsProvider credentialsProvider =
                (SystemDefaultCredentialsProvider) getPrivateFinalField(httpClient.getClass(), httpClient, "credentialsProvider");
        assertNotNull(credentialsProvider);
    }

    @Test
    public void test2() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URI.create("https://google.com"), String.class);

        assertEquals(200, response.getStatusCodeValue());
    }

    private Object getPrivateFinalField(Class t, Object instance, String fieldName) {
        try {
            VarHandle MODIFIERS;
            var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
            MODIFIERS = lookup.findVarHandle(Field.class, "modifiers", int.class);
            Field field = t.getDeclaredField(fieldName);
            field.setAccessible(true);
            MODIFIERS.set(field,
                    field.getModifiers() & ~Modifier.PRIVATE & ~Modifier.FINAL); // 対象アクセス用のFieldオブジェクトのmodifiersからprivateとfinalを外す。
            return field.get(instance);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
