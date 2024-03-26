package com.example.SpeedDriveBackend.services.impl;

import com.example.SpeedDriveBackend.dtos.request.MessageRequest;
import com.example.SpeedDriveBackend.dtos.response.ChatRoomResponse;
import com.example.SpeedDriveBackend.dtos.response.MessageResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.embeddable.ChatRoomId;
import com.example.SpeedDriveBackend.entities.*;
import com.example.SpeedDriveBackend.repositories.AgencyRepository;
import com.example.SpeedDriveBackend.repositories.ChatRoomRepository;
import com.example.SpeedDriveBackend.repositories.ClientRepository;
import com.example.SpeedDriveBackend.repositories.MessageRepository;
import com.example.SpeedDriveBackend.services.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MessageServiceImpl implements MessageService {


    private final ChatRoomRepository chatRoomRepository;
    private final ModelMapper modelMapper;

    private final MessageRepository messageRepository;
    private final AgencyRepository agencyRepository;
    private final ClientRepository clientRepository;



    @Override
    @Transactional
    //this one is with embedded id! keep it later
//public MessageResponse sendMessage(MessageRequest messageRequest) {
//    Message message = modelMapper.map(messageRequest, Message.class);
//    message.setSent(LocalDateTime.now());
//
////    ChatRoomId chatRoomId = messageRequest.getChatRoomId();
//    UUID clientId = messageRequest.getClientId();
//    UUID agencyId = messageRequest.getAgencyId();
//
//    // Construct the ChatRoomId
//    ChatRoomId chatRoomId = new ChatRoomId(clientId, agencyId);
//    // Check if the chat room exists based on the embedded ID
//    Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByChatRoomId(chatRoomId);
//
//    // If the chat room doesn't exist, create a new one
//    ChatRoom chatRoom = optionalChatRoom.orElseGet(() -> {
//        ChatRoom newChatRoom = new ChatRoom();
//        newChatRoom.setChatRoomId(chatRoomId);
//        return chatRoomRepository.save(newChatRoom);
//    });
//
//    message.setChatRoom(chatRoom);
//
//    // Generate a new message ID if not provided
//    if (message.getMessageId() == null) {
//        message.setMessageId(UUID.randomUUID());
//    }
//
//    message = messageRepository.save(message);
//
//    return modelMapper.map(message, MessageResponse.class);
//}
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        Message message = modelMapper.map(messageRequest, Message.class);
        message.setSent(LocalDateTime.now());

        ChatRoom chatRoom = message.getChatRoom();
        if (chatRoom == null || !chatRoomRepository.existsById(chatRoom.getChatRoomId())) {
            chatRoom = new ChatRoom();
            chatRoom.setChatRoomId(UUID.randomUUID());
            chatRoom.setSender(messageRequest.getSender());
            chatRoom.setReceiver(messageRequest.getReceiver());

            chatRoom = chatRoomRepository.save(chatRoom);
        }

        message.setChatRoom(chatRoom);

        if (message.getMessageId() == null) {
            message.setMessageId(UUID.randomUUID());
        }

        message = messageRepository.save(message);

        return modelMapper.map(message, MessageResponse.class);
    }


//        public Message sendMessage(Message message) {
//    message.setSent(LocalDateTime.now());
//
//    ChatRoom chatRoom = message.getChatRoom();
//    if (chatRoom == null || !chatRoomRepository.existsById(chatRoom.getChatroomId())) {
//        chatRoom = new ChatRoom();
//        chatRoom.setChatroomId(UUID.randomUUID());
//        chatRoom = chatRoomRepository.save(chatRoom);
//    }
//
//    message.setChatRoom(chatRoom);
//
//    if (message.getMessageId() == null) {
//        message.setMessageId(UUID.randomUUID());
//    }
//
//    return messageRepository.save(message);
//}


        @Override
//        @Transactional
//        public List<Message> getMessagesByChatRoom(UUID chatRoomId) {
//            return messageRepository.findByChatRoom_ChatRoomIdOrderBySentDesc(chatRoomId);
//        }

    @Transactional
    public List<MessageResponse> getMessagesByChatRoom(UUID chatRoomId) {
        List<Message> messages = messageRepository.findByChatRoom_ChatRoomId(chatRoomId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageResponse.class))
                .collect(Collectors.toList());
    }





    @Override
    @Transactional
//    public List<ChatRoomResponse> getChatRoomsById(UUID id) {
//
//        try {
//            List<ChatRoom> senderChatRooms = chatRoomRepository.findBySender(id);
//            List<ChatRoom> receiverChatRooms = chatRoomRepository.findByReceiver(id);
//
//            List<ChatRoom> allChatRooms = new ArrayList<>();
//            allChatRooms.addAll(senderChatRooms);
//            allChatRooms.addAll(receiverChatRooms);
//
//            List<ChatRoomResponse> responseDtoList = new ArrayList<>();
//            for (ChatRoom chatRoom : allChatRooms) {
//                ChatRoomResponse responseDto = modelMapper.map(chatRoom, ChatRoomResponse.class);
//                responseDtoList.add(responseDto);
//            }
//
//            return responseDtoList;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve chat rooms by ID: " + e.getMessage());
//        }
//
//    }
    public List<ChatRoomResponse> getChatRoomsById(UUID id) {
        try {
            List<ChatRoom> senderChatRooms = chatRoomRepository.findBySender(id);
            List<ChatRoom> receiverChatRooms = chatRoomRepository.findByReceiver(id);

            List<ChatRoomResponse> chatRoomResponses = new ArrayList<>();

            for (ChatRoom chatRoom : senderChatRooms) {
                ChatRoomResponse chatRoomResponse = mapChatRoomToResponse(chatRoom);
                chatRoomResponse.setSender(chatRoom.getSender());
                chatRoomResponse.setReceiver(chatRoom.getReceiver());
                chatRoomResponses.add(chatRoomResponse);
            }

            for (ChatRoom chatRoom : receiverChatRooms) {
                ChatRoomResponse chatRoomResponse = mapChatRoomToResponse(chatRoom);
                chatRoomResponses.add(chatRoomResponse);
            }

            return chatRoomResponses;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve chat rooms by ID: " + e.getMessage());
        }
    }

    private ChatRoomResponse mapChatRoomToResponse(ChatRoom chatRoom) {
        ChatRoomResponse chatRoomResponse = new ChatRoomResponse();
        chatRoomResponse.setChatRoomId(chatRoom.getChatRoomId());

        if (chatRoom.getSender() != null) {
            String senderName = "";
            UUID senderId = chatRoom.getSender();
            Agency agencySender = agencyRepository.findById(senderId).orElse(null);
            if (agencySender != null) {
                senderName = agencySender.getName();
            } else {
                Client clientSender = clientRepository.findById(senderId).orElse(null);
                if (clientSender != null) {
                    senderName = clientSender.getName();
                }
            }
            chatRoomResponse.setSenderName(senderName);
        }

        if (chatRoom.getReceiver() != null) {
            String receiverName = "";
            UUID receiverId = chatRoom.getReceiver();
            Agency agencyReceiver = agencyRepository.findById(receiverId).orElse(null);
            if (agencyReceiver != null) {
                receiverName = agencyReceiver.getName();
            } else {
                Client clientReceiver = clientRepository.findById(receiverId).orElse(null);
                if (clientReceiver != null) {
                    receiverName = clientReceiver.getName();
                }
            }
            chatRoomResponse.setReceiverName(receiverName);
        }

        return chatRoomResponse;
    }



}
