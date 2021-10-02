package CovidLoveit.Service.Services;

import CovidLoveit.Domain.Models.Client;
import CovidLoveit.Domain.Models.CustomUserDetails;
import CovidLoveit.Repositories.Interfaces.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired
    public CustomUserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> clientOptional = clientRepository.findByUsername(username);

        clientOptional.orElseThrow(() -> {
            logger.warn(String.format("Username {%s} not found in database.", username));
            return new UsernameNotFoundException(String.format("Not found {%s}", username));
        });

        Client client = clientOptional.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        client.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        return new CustomUserDetails(client.getUsername(), client.getPassword(), authorities);
    }
}
