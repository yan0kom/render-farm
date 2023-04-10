package rf.back.api.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import rf.api.RfUserApi;
import rf.api.dto.BaseOutDto;
import rf.api.dto.users.DeleteInDto;
import rf.api.dto.users.SignInInDto;
import rf.api.dto.users.SignInOutDto;
import rf.api.dto.users.SignUpInDto;
import rf.api.dto.users.SignUpOutDto;
import rf.api.dto.users.UsersOutDto;
import rf.domain.entity.RfUser;
import rf.domain.service.RfUserService;

@RestController
class RfUserApiImpl implements RfUserApi {
    private final RfUserService rfUserService;

    public RfUserApiImpl(RfUserService rfUserService) {
        this.rfUserService = rfUserService;
    }

    @Override
    public ResponseEntity<SignUpOutDto> signUp(SignUpInDto dto) {
        var rfUser = new RfUser(dto.getUsername(), dto.getPassword(), dto.getRole(), null);
        rfUserService.signUp(rfUser);
        return ResponseEntity.ok(new SignUpOutDto(rfUser.getUsername(), rfUser.getRole()));
    }

	@Override
	public ResponseEntity<BaseOutDto> signOut() {
		rfUserService.signOut();
		return ResponseEntity.ok(new BaseOutDto(true));
	}


    @Override
    public ResponseEntity<SignInOutDto> signIn(SignInInDto dto) {
        var rfUser = new RfUser(dto.getUsername(), dto.getPassword(), null, null);
        rfUser = rfUserService.signIn(rfUser);
        return ResponseEntity.ok(new SignInOutDto(rfUser.getToken(), rfUser.getRole()));
    }

    @Override
    public ResponseEntity<UsersOutDto> list() {
    	var dto = new UsersOutDto();
    	rfUserService.getAllUsers()
    			.map(rfUser -> new UsersOutDto.User(rfUser.getUsername(), rfUser.getRole()))
    			.forEach(dto::addUser);
        return ResponseEntity.ok(dto);
    }

	@Override
	public ResponseEntity<BaseOutDto> delete(DeleteInDto dto) {
		rfUserService.deleteUser(new RfUser(dto.getUsername(), null, null, null));
		return ResponseEntity.ok(new BaseOutDto(true));
	}

}
