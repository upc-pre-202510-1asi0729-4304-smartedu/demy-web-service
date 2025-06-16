package com.smartedu.demy.platform.iam.application.internal.queryservices;

import com.smartedu.demy.platform.iam.domain.model.aggregates.UserAccount;
import com.smartedu.demy.platform.iam.domain.model.valueobjects.Roles;
import com.smartedu.demy.platform.iam.domain.services.UserAccountCommandService;
import com.smartedu.demy.platform.iam.infrastructure.persistence.jpa.repositories.UserAccountRepository;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.CreateTeacherResource;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UpdateTeacherResource;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.UpdateAdminResource;
import com.smartedu.demy.platform.iam.interfaces.rest.resources.ResetPasswordResource;
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
}
