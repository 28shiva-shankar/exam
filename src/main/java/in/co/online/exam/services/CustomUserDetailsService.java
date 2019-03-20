package in.co.online.exam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.co.online.exam.repositories.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private StudentRepository studentRepo;

	@Override
	public UserDetails loadUserByUsername(String rollNo) throws UsernameNotFoundException {
		
		return  studentRepo.getByRollNo(rollNo);
	}

}
