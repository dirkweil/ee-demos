package de.gedoplan.buch.eedemos.rs.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.buch.eedemos.rs.entity.Track;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TrackRepository extends SingleIdEntityRepository<Integer, Track>
{
}
