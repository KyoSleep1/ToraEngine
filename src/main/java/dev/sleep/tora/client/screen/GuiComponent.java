package dev.sleep.tora.client.screen;

import dev.sleep.tora.util.math.Vector2f;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiComponent {

    private GuiComponent parent;

    private int[] clippingBounds;
    private int level, preferredPixelSize;
    private float preferredAspect;

    private Vector2f position, scale, relativePosition, relativeScale;
    private boolean initialized, visible, focus, useCenteringX, useCenteringY, usePreferredAspectFixedX, usePreferredAspectFixedY, specificLevelSet;

    private List<GuiComponent> childComponents, compontentsToRemove, compontentsToAdd;
    private Map<Text, Vector3f> componentTexts;
    private List<Text> textsToRemove;


    public GuiComponent() {
        this.position = new Vector2f();
        this.scale = new Vector2f();
        this.relativePosition = new Vector2f();
        this.relativeScale = new Vector2f();
        this.visible = true;
        this.childComponents = new ArrayList<>();
        this.componentTexts = new HashMap<Text, Vector3f>();
        this.componentsToRemove = new ArrayList<GuiComponent>();
        this.componentsToAdd = new ArrayList<GuiComponent>();
        this.textsToRemove = new ArrayList<Text>();
        this.initialized = false;
        this.focus = false;
        this.useCenteringX = false;
        this.useCenteringY = false;
        this.usePreferredAspectFixedX = false;
        this.usePreferredAspectFixedY = false;
        this.preferredAspect = 1.0f;
        this.specificLevelSet = false;
        this.level = 0;
        this.preferredPixelSize = 1;
    }

    public void show(final boolean visible) {
        this.visible = visible;
    }

    public void addComponent(final GuiComponent component, final float relX, final float relY, final float relScaleX, final float relScaleY) {
        component.relativePosition.set(relX, relY);
        component.relativeScale.set(relScaleX, relScaleY);
        component.parent = this;
        this.componentsToAdd.add(component);
    }

    public void addComponentX(final GuiComponent component, final float relX, final float relY, final float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.relativePosition.set(relX, relY);
        component.relativeScale.x = relScaleX;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void addComponentY(final GuiComponent component, final float relX, final float relY, final float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.relativePosition.set(relX, relY);
        component.relativeScale.y = relScaleY;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void addPixelComp(final GuiComponent component, final float relX, final float relY) {
        if (!this.initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        final float width = component.preferredPixelSize / (this.scale.x * DisplayManager.getUiWidth());
        final float height = component.preferredPixelSize / (this.scale.y * DisplayManager.getUiHeight());
        this.addComponent(component, relX, relY, width, height);
    }

    public void addPixelCompCenterX(final GuiComponent component, final float centerX, final float relY) {
        if (!this.initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        final float width = component.preferredPixelSize / (this.scale.x * DisplayManager.getUiWidth());
        final float height = component.preferredPixelSize / (this.scale.y * DisplayManager.getUiHeight());
        this.addComponent(component, centerX - width * 0.5f, relY, width, height);
    }

    public void addPixelCompCenterY(final GuiComponent component, final float relX, final float centerY) {
        if (!this.initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        final float width = component.preferredPixelSize / (this.scale.x * DisplayManager.getUiWidth());
        final float height = component.preferredPixelSize / (this.scale.y * DisplayManager.getUiHeight());
        this.addComponent(component, relX, centerY - height * 0.5f, width, height);
    }

    public void addPixelCompCenter(final GuiComponent component, final float centerX, final float centerY) {
        if (!this.initialized) {
            System.err.println("UI Component must be initialized before adding PP component!");
        }
        final float width = component.preferredPixelSize / (this.scale.x * DisplayManager.getUiWidth());
        final float height = component.preferredPixelSize / (this.scale.y * DisplayManager.getUiHeight());
        this.addComponent(component, centerX - width * 0.5f, centerY - height * 0.5f, width, height);
    }

    public void addCenteredComponentX(final GuiComponent component, final float centerX, final float relY, final float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.useCenteringX = true;
        component.relativePosition.set(centerX, relY);
        component.relativeScale.y = relScaleY;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponent(final GuiComponent component, final float centerX, final float centerY, final float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.useCenteringX = true;
        component.useCenteringY = true;
        component.relativePosition.set(centerX, centerY);
        component.relativeScale.x = relScaleX;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponentY(final GuiComponent component, final float centerY, final float relX, final float relScaleX) {
        component.usePreferredAspectFixedX = true;
        component.useCenteringY = true;
        component.relativePosition.set(relX, centerY);
        component.relativeScale.x = relScaleX;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void addCenteredComponentYScaleY(final GuiComponent component, final float centerY, final float relX, final float relScaleY) {
        component.usePreferredAspectFixedY = true;
        component.useCenteringY = true;
        component.relativePosition.set(relX, centerY);
        component.relativeScale.y = relScaleY;
        this.componentsToAdd.add(component);
        component.parent = this;
    }

    public void setPreferredPixelSize(final int size) {
        this.preferredPixelSize = size;
    }

    public void setFocus(final boolean focus) {
        this.hasFocus = focus;
        this.setChildrenFocus();
    }

    private void setChildrenFocus() {
        for (final GuiComponent child : this.childComponents) {
            child.hasFocus = this.hasFocus;
            child.setChildrenRenderLevel();
        }
    }

    public void setRenderLevel(final int level) {
        this.level = level;
        this.specificLevelSet = true;
        this.setChildrenRenderLevel();
    }

    public void removeComponent(final GuiComponent component) {
        this.componentsToRemove.add(component);
    }

    public void clear() {
        this.componentsToRemove.addAll(this.childComponents);
        this.deleteTexts();
    }

    public void addText(final Text text, final float relX, final float relY, final float relLineWidth) {
        final Vector3f relativePosition = new Vector3f(relX, relY, relLineWidth);
        text.setParentInfo(this, relativePosition);
        text.setClippingBounds(this.clippingBounds);
        this.componentTexts.put(text, relativePosition);
        if (this.initialized) {
            this.setTextScreenSpacePosition(text, relativePosition);
        }
    }

    protected boolean isInitialized() {
        return this.initialized;
    }

    public boolean isShown() {
        return this.visible;
    }

    public void deleteText(final Text text) {
        this.textsToRemove.add(text);
    }

    public float getRelativeX() {
        return this.relativePosition.x;
    }

    public float getRelativeY() {
        return this.relativePosition.y;
    }

    public float getRelativeScaleY() {
        return this.relativeScale.y;
    }

    public float getRelativeScaleX() {
        return this.relativeScale.x;
    }

    public void setRelativeX(final float x) {
        if (this.relativePosition.x != x) {
            this.relativePosition.x = x;
            this.updateScreenSpacePosition();
        }
    }

    public void setRelativeY(final float y) {
        if (this.relativePosition.y != y) {
            this.relativePosition.y = y;
            this.updateScreenSpacePosition();
        }
    }

    public void setRelativeScaleX(final float relScaleX) {
        if (this.relativeScale.x != relScaleX) {
            this.relativeScale.x = relScaleX;
            this.updateScreenSpacePosition();
        }
    }

    public void setRelativeScaleY(final float relScaleY) {
        if (this.relativeScale.y != relScaleY) {
            this.relativeScale.y = relScaleY;
            this.updateScreenSpacePosition();
        }
    }

    public void setRelativeScale(final float relScaleX, final float relScaleY) {
        if (this.relativeScale.x != relScaleX || this.relativeScale.y != relScaleY) {
            this.relativeScale.x = relScaleX;
            this.relativeScale.y = relScaleY;
            this.updateScreenSpacePosition();
        }
    }

    public void remove() {
        this.parent.removeComponent(this);
    }

    public void increaseRelativePosition(final float dX, final float dY) {
        final Vector2f relativePosition = this.relativePosition;
        relativePosition.x += dX;
        final Vector2f relativePosition2 = this.relativePosition;
        relativePosition2.y += dY;
        this.updateScreenSpacePosition();
    }

    public float getRelativeAspectRatio() {
        return this.relativeScale.x / this.relativeScale.y;
    }

    public void setRelativePosition(final float x, final float y) {
        if (this.relativePosition.x != x || this.relativePosition.y != y) {
            this.relativePosition.x = x;
            this.relativePosition.y = y;
            this.updateScreenSpacePosition();
        }
    }

    public void setPreferredAspectRatio(final float ratio) {
        this.preferredAspect = ratio;
    }

    public void updateTextAbsPos(final Text text) {
        this.setTextScreenSpacePosition(text, this.componentTexts.get(text));
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public float getPixelHeight() {
        return this.scale.y * DisplayManager.getUiHeight();
    }

    public float getPixelWidth() {
        return this.scale.x * DisplayManager.getUiWidth();
    }

    public final boolean isMouseOver() {
        return GuiMaster.isInFocus(this) && this.isMouseOverFocusIrrelevant();
    }

    public boolean isMouseOverFocusIrrelevant() {
        if (!GuiMaster.isMouseInteractionEnabled()) {
            return false;
        }
        final MyMouse mouse = MyMouse.getActiveMouse();
        return mouse.getX() >= this.position.x && mouse.getX() <= this.position.x + this.scale.x && mouse.getY() >= this.position.y && mouse.getY() <= this.position.y + this.scale.y;
    }

    protected boolean inFocus() {
        return this.hasFocus;
    }

    public int getLevel() {
        return this.level;
    }

    public float getRelativeMouseX() {
        final MyMouse mouse = MyMouse.getActiveMouse();
        return (mouse.getX() - this.position.x) / this.scale.x;
    }

    public float getRelativeMouseY() {
        final MyMouse mouse = MyMouse.getActiveMouse();
        return (mouse.getY() - this.position.y) / this.scale.y;
    }

    protected void setClippingBounds(final float x, final float y, final float width, final float height) {
        final int xPixels = Math.round(x * Display.getWidth());
        final int yPixels = Display.getHeight() - Math.round((y + height) * Display.getHeight());
        final int widthPixels = Math.round(width * Display.getWidth());
        final int heightPixels = Math.round(height * Display.getHeight());
        if (this.clippingBounds == null) {
            final int[] bounds = {xPixels, yPixels, widthPixels, heightPixels};
            this.setChildrenClippingBounds(bounds);
        } else {
            this.clippingBounds[0] = xPixels;
            this.clippingBounds[1] = yPixels;
            this.clippingBounds[2] = widthPixels;
            this.clippingBounds[3] = heightPixels;
        }
    }

    protected void setTextureClippingBounds(final int[] bounds) {
    }

    protected float pixelsToRelativeX(final float pixels) {
        final float pixelsWide = DisplayManager.getUiWidth() * this.scale.x;
        return pixels / pixelsWide;
    }

    protected float pixelsToRelativeY(final float pixels) {
        final float pixelsHigh = DisplayManager.getUiHeight() * this.scale.y;
        return pixels / pixelsHigh;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    protected void forceInitialization(final float absX, final float absY, final float absScaleX, final float absScaleY) {
        this.position.x = absX;
        this.position.y = absY;
        this.scale.x = absScaleX;
        this.scale.y = absScaleY;
        this.initialized = true;
    }

    protected final void update(final GuiRenderData data) {
        if (!this.visible) {
            return;
        }
        this.updateSelf();
        this.updateTexts();
        this.getGuiTextures(data);
        this.addTextsToRenderBatch(data);
        this.addNewChildren();
        this.removeOldComponents();
        for (final GuiComponent childComponent : this.childComponents) {
            childComponent.update(data);
        }
    }

    protected List<GuiComponent> getComponents() {
        return this.childComponents;
    }

    protected float getRelativeHeightCoords(float relativeWidth) {
        relativeWidth *= DisplayManager.getUiWidth() / (float) DisplayManager.getUiHeight();
        relativeWidth *= this.scale.x / this.scale.y;
        return relativeWidth;
    }

    protected float getRelativeWidthCoords(float relativeHeight) {
        relativeHeight /= DisplayManager.getUiWidth() / (float) DisplayManager.getUiHeight();
        relativeHeight /= this.scale.x / this.scale.y;
        return relativeHeight;
    }

    protected void init() {
    }

    protected void updateScreenSpacePosition() {
        this.calculateAbsPositionAndScale();
        this.updateGuiTexturePositions(this.position, this.scale);
        for (final GuiComponent component : this.childComponents) {
            component.updateScreenSpacePosition();
        }
        for (final Text text : this.componentTexts.keySet()) {
            this.setTextScreenSpacePosition(text, this.componentTexts.get(text));
        }
    }

    protected abstract void updateGuiTexturePositions(final Vector2f p0, final Vector2f p1);

    protected abstract void updateSelf();

    protected abstract void getGuiTextures(final GuiRenderData p0);

    protected void delete() {
        this.deleteTexts();
        for (final GuiComponent component : this.componentsToRemove) {
            component.delete();
        }
        for (final GuiComponent component : this.componentsToAdd) {
            component.delete();
        }
        for (final GuiComponent component : this.childComponents) {
            component.delete();
        }
    }

    private void setChildrenClippingBounds(final int[] bounds) {
        this.setTextureClippingBounds(this.clippingBounds = bounds);
        for (final Text text : this.componentTexts.keySet()) {
            text.setClippingBounds(this.clippingBounds);
        }
        for (final GuiComponent child : this.childComponents) {
            child.setChildrenClippingBounds(this.clippingBounds);
        }
    }

    private void setChildrenRenderLevel() {
        for (final GuiComponent child : this.childComponents) {
            if (!child.specificLevelSet) {
                child.level = this.level;
                child.setChildrenRenderLevel();
            }
        }
    }

    private void addTextsToRenderBatch(final GuiRenderData data) {
        for (final Text text : this.componentTexts.keySet()) {
            data.addText(text.getRenderLevel(), text);
        }
    }

    private void deleteTexts() {
        for (final Text text : this.componentTexts.keySet()) {
            text.deleteFromMemory();
        }
        this.componentTexts.clear();
    }

    private void setTextScreenSpacePosition(final Text text, final Vector3f relativePosition) {
        final float x = this.position.x + this.scale.x * relativePosition.x;
        final float y = this.position.y + this.scale.y * relativePosition.y;
        final float lineWidth = relativePosition.z * this.scale.x;
        text.initialise(x, y, lineWidth);
    }

    private void updateTexts() {
        for (final Text text : this.textsToRemove) {
            this.componentTexts.remove(text);
            text.deleteFromMemory();
        }
        this.textsToRemove.clear();
        for (final Text text : this.componentTexts.keySet()) {
            text.update(DisplayManager.getDeltaSeconds());
        }
    }

    private void removeOldComponents() {
        while (!this.componentsToRemove.isEmpty()) {
            final GuiComponent component = this.componentsToRemove.remove(0);
            this.childComponents.remove(component);
            component.delete();
        }
    }

    private void addNewChildren() {
        int index = 0;
        while (index < this.componentsToAdd.size()) {
            final GuiComponent component = this.componentsToAdd.get(index++);
            this.childComponents.add(component);
            component.clippingBounds = this.clippingBounds;
            component.hasFocus = this.hasFocus;
            if (!component.specificLevelSet) {
                component.level = this.level;
            }
            component.updateScreenSpacePosition();
            component.init();
            component.setTextureClippingBounds(this.clippingBounds);
            component.addNewChildren();
        }
        this.componentsToAdd.clear();
    }

    private void calculateAbsPositionAndScale() {
        this.position.x = this.parent.position.x + this.parent.scale.x * this.relativePosition.x;
        this.position.y = this.parent.position.y + this.parent.scale.y * this.relativePosition.y;
        this.scale.x = this.relativeScale.x * this.parent.scale.x;
        this.scale.y = this.relativeScale.y * this.parent.scale.y;
        if (this.usePreferredAspectFixedY) {
            this.scale.x = this.convertToScreenWidthCoords(this.scale.y);
            this.relativeScale.x = this.scale.x / this.parent.scale.x;
            this.usePreferredAspectFixedY = false;
        } else if (this.usePreferredAspectFixedX) {
            this.scale.y = this.convertToScreenHeightCoords(this.scale.x);
            this.relativeScale.y = this.scale.y / this.parent.scale.y;
            this.usePreferredAspectFixedX = false;
        }
        if (this.useCenteringX) {
            this.position.x -= this.scale.x / 2.0f;
            this.relativePosition.x = (this.position.x - this.parent.position.x) / this.parent.scale.x;
            this.useCenteringX = false;
        }
        if (this.useCenteringY) {
            this.position.y -= this.scale.y / 2.0f;
            this.relativePosition.y = (this.position.y - this.parent.position.y) / this.parent.scale.y;
            this.useCenteringY = false;
        }
        this.initialized = true;
    }

    private float convertToScreenWidthCoords(float heightCoord) {
        heightCoord /= DisplayManager.getAspectRatio();
        heightCoord *= this.preferredAspect;
        return heightCoord;
    }

    private float convertToScreenHeightCoords(float widthCoord) {
        widthCoord *= DisplayManager.getAspectRatio();
        widthCoord /= this.preferredAspect;
        return widthCoord;
    }
}
