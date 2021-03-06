/*
 * File created on Apr 14, 2016
 *
 * Copyright (c) 2016 Carl Harris, Jr
 * and others as noted
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soulwing.prospecto.demo.jaxrs.service.view;

import java.util.Collection;

import org.soulwing.prospecto.api.association.AssociationDescriptor;
import org.soulwing.prospecto.api.association.ToManyAssociationManager;
import org.soulwing.prospecto.demo.jaxrs.domain.Division;
import org.soulwing.prospecto.demo.jaxrs.domain.Player;
import org.soulwing.prospecto.demo.jaxrs.domain.Team;

/**
 * A {@link ToManyAssociationManager} that manages the relationship between
 * a {@link Division} and its {@link Team} elements.
 *
 * @author Carl Harris
 */
class DivisionTeamAssociationManager
    extends AbstractEntityCollectionAssociationManager<Division, Team> {

  static final DivisionTeamAssociationManager INSTANCE =
      new DivisionTeamAssociationManager();

  private DivisionTeamAssociationManager() {}

  @Override
  public boolean supports(AssociationDescriptor descriptor) {
    return Division.class.isAssignableFrom(descriptor.getOwnerType())
        && Team.class.isAssignableFrom(descriptor.getAssociateType());
  }

  @Override
  public boolean add(Division division, Team team) throws Exception {
    return division.addTeam(team);
  }

  @Override
  public boolean remove(Division division, Team team) throws Exception {
    return division.removeTeam(team);
  }

  @Override
  public void clear(Division division) throws Exception {
    for (final Team team : division.getTeams()) {
      team.setDivision(null);
    }
    division.getTeams().clear();
  }

  @Override
  protected Collection<Team> getAssociates(Division division) throws Exception {
    return division.getTeams();
  }

}
