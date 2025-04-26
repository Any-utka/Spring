package com.i2303.coursework.transactions.CourseworkTransactions.service;

import com.i2303.coursework.transactions.CourseworkTransactions.dto.PublisherDTO;
import com.i2303.coursework.transactions.CourseworkTransactions.entity.Publisher;
import com.i2303.coursework.transactions.CourseworkTransactions.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public PublisherDTO addPublisher(PublisherDTO publisherDTO) {
        Publisher publisher = new Publisher();
        publisher.setName(publisherDTO.getName());
        publisherRepository.save(publisher);
        return new PublisherDTO(publisher);
    }

    public PublisherDTO getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        return new PublisherDTO(publisher);
    }

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        publisher.setName(publisherDTO.getName());
        publisherRepository.save(publisher);
        return new PublisherDTO(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    public List<PublisherDTO> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherDTO::new)
                .collect(Collectors.toList());
    }
}
