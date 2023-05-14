package com.digdes.school.repos.impl;

import com.digdes.school.models.Member;
import com.digdes.school.repos.MemberRepository;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemberRepositoryImpl implements MemberRepository<Member> {

    private final List<Member> members;
    private final Path fileStoragePath = Path.of("data-storage.txt");
    private final AtomicLong idGenerator;

    public MemberRepositoryImpl() {
        members = loadMembers();
        idGenerator = new AtomicLong(
                members.stream()
                        .map(Member::getId)
                        .max(Long::compareTo)
                        .orElse(0L)
        );
    }

    private List<Member> loadMembers() {
        List<Member> result = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(fileStoragePath))) {
            result = (ArrayList<Member>) ois.readObject();
        } catch (EOFException e) {
            result = new ArrayList<>();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void saveMembers(List<Member> members) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(fileStoragePath))) {
            oos.writeObject(members);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Member createMember(Member member) {
        member.setId(idGenerator.incrementAndGet());
        members.add(member);
        saveMembers(members);
        return member;
    }

    @Override
    public Member updateMember(Member member) {
        removeMemberFromCache(member.getId());
        members.add(member);
        saveMembers(members);
        return member;
    }

    @Override
    public Member deleteById(Long id) {
        Member memberToDelete = removeMemberFromCache(id);
        saveMembers(members);
        return memberToDelete;
    }

    private Member removeMemberFromCache(Long id) {
        Member memberToRemove = members.stream()
                .filter(m -> m.getId().equals(id))
                .findAny()
                .orElseThrow(RuntimeException::new);
        members.remove(memberToRemove);
        return memberToRemove;
    }

    @Override
    public List<Member> searchMembers(String pattern) {
        return null;
    }

    @Override
    public Optional<Member> getById(Long id) {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Member> getAll() {
        return members;
    }
}
