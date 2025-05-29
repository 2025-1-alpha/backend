package com.geulowup.backend.domain.template.listener;

import com.geulowup.backend.domain.template.entity.UserTemplateFolder;
import com.geulowup.backend.domain.template.repository.UserFolderRepository;
import com.geulowup.backend.domain.user.entity.User;
import com.geulowup.backend.domain.user.event.UserSignupEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class FolderEventListener {

    private final UserFolderRepository userFolderRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onSignup(UserSignupEvent event) {
        User user = event.user();
        String folderName = user.getName() + "님의 기본 폴더";

        if (userFolderRepository.existsByUserAndName(user, folderName)) {
            log.warn("신규 가입 유저지만 기본 폴더가 이미 존재함: userId={}", user.getId());
            return;
        }

        UserTemplateFolder folder = UserTemplateFolder.builder()
                .user(user)
                .name(folderName)
                .build();

        userFolderRepository.save(folder);
        log.info("기본 폴더 생성: userId={}, folderName={}", user.getId(), folder.getName());
    }
}
