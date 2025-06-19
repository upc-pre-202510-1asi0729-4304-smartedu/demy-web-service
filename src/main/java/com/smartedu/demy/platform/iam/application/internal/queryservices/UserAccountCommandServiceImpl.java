package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.*;
import com.smartedu.demy.platform.shared.domain.model.services.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class UserAccountCommandServiceImpl implements UserAccountCommandService {

    private final UserAccountRepository repo;

    public UserAccountCommandServiceImpl(UserAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserAccount createTeacher(CreateTeacherResource r) {
        var user = r.toEntity();
        user.updateRole(Roles.TEACHER);
        return repo.save(user);
    }

    @Override
    public UserAccount updateTeacher(Long id, UpdateTeacherResource r) {
        var user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        r.applyTo(user);
        return repo.save(user);
    }

    @Override
    public UserAccount updateAdmin(Long id, UpdateAdminResource r) {
        var user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        r.applyTo(user);
        return repo.save(user);
    }

    @Override
    public void deleteTeacher(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void resetPassword(ResetPasswordResource resource) {
        UserAccount user = repo.findByEmail_Value(resource.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updatePassword(PasswordHasher.hash(resource.newPassword()));
        repo.save(user);
    }

    @Override
    public UserAccount signUpAdmin(SignUpAdminResource r) {
        repo.findByEmail_Value(r.email())
                .ifPresent(u -> { throw new RuntimeException("Email already registered"); });

        var admin = r.toEntity();
        // Por claridad, aunque ya viene con el rol ADMIN desde toEntity()
        admin.updateRole(Roles.ADMIN);

        return repo.save(admin);
    }

    @Override
    public UserAccount signInAdmin(SignInAdminResource r) {
        var admin = repo.findByEmail_Value(r.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (admin.getRole() != Roles.ADMIN)
            throw new RuntimeException("User is not an admin");

        if (!PasswordHasher.matches(r.password(), admin.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");

        return admin;
    }


    @Override
    public UserAccount signInTeacher(SignInTeacherResource r) {
        var teacher = repo.findByEmail_Value(r.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (teacher.getRole() != Roles.TEACHER)
            throw new RuntimeException("User is not a teacher");

        if (!PasswordHasher.matches(r.password(), teacher.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");

        return teacher;
    }

}
