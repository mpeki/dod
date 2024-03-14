package dk.dodgame.system.rule;

import dk.dodgame.util.rules.RuleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoDRule {
    String name() default "";
    RuleType type();
}
