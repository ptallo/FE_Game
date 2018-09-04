package model;

import components.combat.CombatComponent;
import components.physics.PhysicsComponent;
import components.render.RenderComponent;

public interface ObjectInterface {
    PhysicsComponent getPhysicsComponent();
    RenderComponent getRenderComponent();
    CombatComponent getCombatComponent();
}
