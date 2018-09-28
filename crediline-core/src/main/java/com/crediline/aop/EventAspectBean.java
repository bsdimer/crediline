package com.crediline.aop;

import org.aspectj.lang.annotation.Aspect;


/**
 * Created by dimer on 3/31/14.
 */
@Aspect
public class EventAspectBean {


    /*@Pointcut("execution(* com.crediline.service.*(..))")
        public void selectAllServiceMethods() {
        }*//*
    @Resource(name = EventManager.BEAN_REFERENCE_NAME)
    private EventManager eventManager;

    public static Logger logger = Logger.getLogger(EventAspectBean.class.getName());

    public EventAspectBean() {

    }

    @Pointcut("execution(* com.crediline.service.*.save(..)) && !execution(* com.crediline.event.*.save(..))")
    public void selectAllServiceSaveMethods() {
    }

    @Pointcut("execution(* com.crediline.service.*.delete(..)) && !execution(* com.crediline.event.*.delete(..))")
    public void selectAllServiceDeleteMethods() {
    }

    @Pointcut("execution(* com.crediline.service.*.update(..)) && !execution(* com.crediline.event.*.update(..))")
    public void selectAllServiceUpdateMethods() {
    }


    @After("selectAllServiceSaveMethods()")
    public void afterServiceSave(final JoinPoint jp) {
        *//*try {
            Event event = new Event();
            String eventValue = LocaleUtils.getLocaliziedMessage("common.saveEvent");
            StringBuilder sb = new StringBuilder(eventValue);
            if (jp.getArgs().length > 0) {
                sb.append(jp.getArgs()[0].toString());
            }
            event.setValue(sb.toString());
            getEventManager().pushEvent(event);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }*//*
    }

    @After("selectAllServiceUpdateMethods()")
    public void afterServiceUpdate(final JoinPoint jp) {
        *//*try {
            Event event = new Event();
            String eventValue = LocaleUtils.getLocaliziedMessage("common.updateEvent");
            StringBuilder sb = new StringBuilder(eventValue);
            if (jp.getArgs().length > 0) {
                sb.append(jp.getArgs()[0].toString());
            }
            event.setValue(sb.toString());
            getEventManager().pushEvent(event);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }*//*
    }

    @After("selectAllServiceDeleteMethods()")
    public void afterServiceDelete(final JoinPoint jp) {
        *//*try {
            Event event = new Event();
            String eventValue = LocaleUtils.getLocaliziedMessage("common.deleteEvent");
            StringBuilder sb = new StringBuilder(eventValue);
            if (jp.getArgs().length > 0) {
                sb.append(jp.getArgs()[0].toString());
            }
            event.setValue(sb.toString());
            getEventManager().pushEvent(event);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }*//*
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }*/
}
