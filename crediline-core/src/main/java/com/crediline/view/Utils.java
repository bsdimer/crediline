package com.crediline.view;

import org.primefaces.component.tabview.Tab;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by dimer on 1/17/14.
 */
public class Utils {

    public static UIComponent findComponent(final String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];
        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });
        return found[0];
    }

    public static Tab getTab(String id) {
        Tab tab = new Tab();
        tab.setTitle(id);
        tab.getChildren().add(findComponent(id));
        return tab;
    }

    /**
     * Copied from faces-impl
     * <p/>
     * <p class="changed_added_2_0">A VisitContext implementation that is
     * used when performing a full component tree visit.</p>
     * <p/>
     * RELEASE_PENDING
     *
     * @since 2.0
     */
    public static class FullVisitContext extends VisitContext {

        /**
         * Creates a FullVisitorContext instance.
         *
         * @param facesContext the FacesContext for the current request
         * @throws NullPointerException if {@code facesContext}
         *                              is {@code null}
         */
        public FullVisitContext(FacesContext facesContext) {
            this(facesContext, null);
        }

        /**
         * Creates a FullVisitorContext instance with the specified
         * hints.
         *
         * @param facesContext the FacesContext for the current request
         * @param hints        a the VisitHints for this visit
         * @throws NullPointerException if {@code facesContext}
         *                              is {@code null}
         */
        public FullVisitContext(FacesContext facesContext,
                                Set<VisitHint> hints) {

            if (facesContext == null) {
                throw new NullPointerException();
            }

            this.facesContext = facesContext;

            // Copy and store hints - ensure unmodifiable and non-empty
            EnumSet<VisitHint> hintsEnumSet = ((hints == null) || (hints.isEmpty()))
                    ? EnumSet.noneOf(VisitHint.class)
                    : EnumSet.copyOf(hints);

            this.hints = Collections.unmodifiableSet(hintsEnumSet);
        }

        /**
         * @see VisitContext#getFacesContext VisitContext.getFacesContext()
         */
        @Override
        public FacesContext getFacesContext() {
            return facesContext;
        }

        /**
         * @see VisitContext#getIdsToVisit VisitContext.getIdsToVisit()
         */
        @Override
        public Collection<String> getIdsToVisit() {

            // We always visits all ids
            return ALL_IDS;
        }

        /**
         * @see VisitContext#getSubtreeIdsToVisit VisitContext.getSubtreeIdsToVisit()
         */
        @Override
        public Collection<String> getSubtreeIdsToVisit(UIComponent component) {

            // Make sure component is a NamingContainer
            if (!(component instanceof NamingContainer)) {
                throw new IllegalArgumentException("Component is not a NamingContainer: " + component);
            }

            // We always visits all ids
            return ALL_IDS;
        }

        /**
         * @see VisitContext#getHints VisitContext.getHints
         */
        @Override
        public Set<VisitHint> getHints() {
            return hints;
        }

        /**
         * @see VisitContext#invokeVisitCallback VisitContext.invokeVisitCallback()
         */
        @Override
        public VisitResult invokeVisitCallback(UIComponent component,
                                               VisitCallback callback) {

            // Nothing interesting here - just invoke the callback.
            // (PartialVisitContext.invokeVisitCallback() does all of the
            // interesting work.)
            return callback.visit(this, component);
        }

        // The FacesContext for this request
        private FacesContext facesContext;

        // Our visit hints
        private Set<VisitHint> hints;
    }
}
