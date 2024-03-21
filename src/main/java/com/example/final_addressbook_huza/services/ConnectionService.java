package com.example.final_addressbook_huza.services;

import com.example.final_addressbook_huza.data.Connection;
import com.example.final_addressbook_huza.repositories.ConnectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConnectionService {

    ConnectionRepository connectionRepository;

    public List<Connection> getConnections() {
        return connectionRepository.findAll();
    }

    public Optional<Connection> getConnectionById(Integer connectionId) {
        return connectionRepository.findById(connectionId);
    }
}
