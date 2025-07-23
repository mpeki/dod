package dk.dodgame.system.queue;

//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;

//@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME = "dodCombatQueue";
    public static final String EXCHANGE_NAME = "dodCombatExchange";
    public static final String ROUTING_KEY = "dod.combat.start";

//    @Bean
//    public Queue longRunningTaskQueue() {
//        return new Queue(QUEUE_NAME, true); // durable queue
//    }

//    @Bean
//    public DirectExchange longRunningTaskExchange() {
//        return new DirectExchange(EXCHANGE_NAME);
//    }

//    @Bean
//    public Binding binding(Queue longRunningTaskQueue, DirectExchange longRunningTaskExchange) {
//        return BindingBuilder.bind(longRunningTaskQueue).to(longRunningTaskExchange).with(ROUTING_KEY);
//    }
}
