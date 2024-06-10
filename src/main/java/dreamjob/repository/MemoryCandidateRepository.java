package dreamjob.repository;
import dreamjob.model.Candidate;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidaties = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan Ivanov", "good", LocalDateTime.now()));
        save(new Candidate(0, "Sergei Petrov", "good", LocalDateTime.now()));
        save(new Candidate(0, "Artem Zivoi", "good", LocalDateTime.now()));
        save(new Candidate(0, "Roman Khoroshi", "good", LocalDateTime.now()));
        save(new Candidate(0, "Evgen Zukov", "good", LocalDateTime.now()));
        save(new Candidate(0, "Zelana Belaya", "good girl", LocalDateTime.now()));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidaties.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidaties.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidaties.computeIfPresent(candidate.getId(),
                (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription(), candidate.getCreationDate())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidaties.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidaties.values();
    }
}